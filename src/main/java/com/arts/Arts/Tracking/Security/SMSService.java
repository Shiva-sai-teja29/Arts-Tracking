package com.arts.Arts.Tracking.Security;

import com.arts.Arts.Tracking.Users.UserDTO.ValidateOtpRequest;

public interface SMSService {
    Object sendSms(String phoneNumber, String message) throws Exception;

    String getOtpCode(String phoneNumber);

    boolean validateOtp(ValidateOtpRequest otp);
}
