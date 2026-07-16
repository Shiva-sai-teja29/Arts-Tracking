package com.arts.Arts.Tracking.Security.Oauth2;

import com.arts.Arts.Tracking.DTO.AuthProvider;
import com.arts.Arts.Tracking.Entity.RefreshToken;
import com.arts.Arts.Tracking.Entity.Role;
import com.arts.Arts.Tracking.Entity.User;
import com.arts.Arts.Tracking.Repo.UserRepository;
import com.arts.Arts.Tracking.Security.Config.JwtService;
import com.arts.Arts.Tracking.Security.UserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (!(authentication.getPrincipal() instanceof OAuth2User user)) {
            throw new IllegalStateException("OAuth2 authentication failed");
        }

        String email = user.getAttribute("email");
            String name = user.getAttribute("name");
            String providerId = user.getAttribute("sub");
        String picture = user.getAttribute("picture");

            // Generate JWT
            String token = jwtService.generateToken(email);

        RefreshToken refreshToken;

        Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isEmpty()){
                User user1 = new User();
                user1.setEmail(email);
                user1.setUsername(name);
                user1.setProvider(AuthProvider.GOOGLE);
                user1.setProfileImage(picture);
                user1.setCreatedAt(LocalDateTime.now());
                userRepository.save(user1);

                refreshToken = jwtService.createRefreshTokenForFirstTimeLogin(user1);
            }else {
                refreshToken = jwtService.createRefreshToken(email);
            }

            // Redirect to React with token
//            response.sendRedirect("http://localhost:5173/home?token=" + token);
        response.sendRedirect("http://localhost:5173/oauth-success?token=" + token+ "&refreshToken=" + refreshToken.getToken());
    }
}