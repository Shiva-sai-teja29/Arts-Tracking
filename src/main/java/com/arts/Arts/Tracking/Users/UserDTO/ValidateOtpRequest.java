package com.arts.Arts.Tracking.Users.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOtpRequest {
    private String mobile;
    private String otp;
    private String username;
}
