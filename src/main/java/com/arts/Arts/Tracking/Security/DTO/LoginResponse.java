package com.arts.Arts.Tracking.Security.DTO;

import com.arts.Arts.Tracking.Entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String expiryTime;
    private RefreshToken refreshToken;
}
