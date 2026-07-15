package com.arts.Arts.Tracking.Security;

import com.arts.Arts.Tracking.Entity.RefreshToken;
import com.arts.Arts.Tracking.Entity.Role;
import com.arts.Arts.Tracking.Entity.User;
import com.arts.Arts.Tracking.Repo.RefreshTokenRepository;
import com.arts.Arts.Tracking.Repo.RoleRepository;
import com.arts.Arts.Tracking.Repo.UserRepository;
import com.arts.Arts.Tracking.Security.Config.JwtService;
import com.arts.Arts.Tracking.Security.DTO.LoginRequest;
import com.arts.Arts.Tracking.Security.DTO.LoginResponse;
import com.arts.Arts.Tracking.Security.DTO.RefreshTokenRequest;
import com.arts.Arts.Tracking.Security.DTO.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (!(authentication.getPrincipal() instanceof UserInfo user)) {
            throw new BadCredentialsException("Invalid authentication");
        }

        User user1 = userRepository.findByUsername(user.getUsername())
                .orElseThrow(()->new RuntimeException("User not matched with Role"));

        String accessToken = jwtService.generateToken(user1.getEmail());
        RefreshToken refreshToken = jwtService.createRefreshToken(user1.getEmail());
        LoginResponse resp = new LoginResponse();
        resp.setToken(accessToken);
        resp.setExpiryTime("60");
        resp.setRefreshToken(refreshToken);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        System.out.println("REGISTER API CALLED");
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);


        return ResponseEntity.ok(Map.of("message", "User created"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken = jwtService.verifyRefreshToken(request.getRefreshToken());

        String accessToken = jwtService.generateToken(
                refreshToken.getUser().getEmail());

        LoginResponse response = new LoginResponse();

        response.setToken(accessToken);
        response.setRefreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody RefreshTokenRequest request) {

        refreshTokenRepository.deleteByToken(
                request.getRefreshToken());

        return ResponseEntity.ok("Logged Out");
    }

}
