package com.arts.Arts.Tracking.Security.Config;

import com.arts.Arts.Tracking.Entity.RefreshToken;
import com.arts.Arts.Tracking.Entity.User;
import com.arts.Arts.Tracking.Repo.RefreshTokenRepository;
import com.arts.Arts.Tracking.Repo.UserRepository;
import com.arts.Arts.Tracking.Security.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static SecretKey key;

    public JwtService(@Value("${security.jwt.secret}") String secret) {
        try {
            this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT secret key: " + e.getMessage(), e);
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
               // .claim("roles",roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 120000))
                .signWith(key)
                .compact();
    }

    public RefreshToken createRefreshToken(String userName) {
        RefreshToken refreshToken = new RefreshToken();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException("User not found"));
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(
                LocalDateTime.ofInstant(
                        Instant.now().plusMillis(900000),
                        ZoneId.systemDefault()
                )
        );
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expired");
        }
        return refreshToken;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        Claims claims = extractClaims(token);
        return "refresh".equals(claims.get("type"));
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public RefreshToken createRefreshTokenForFirstTimeLogin(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(
                LocalDateTime.ofInstant(
                        Instant.now().plusMillis(900000),
                        ZoneId.systemDefault()
                )
        );
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
}