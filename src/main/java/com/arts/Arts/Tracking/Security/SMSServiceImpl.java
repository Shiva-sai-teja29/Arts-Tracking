package com.arts.Arts.Tracking.Security;

import com.arts.Arts.Tracking.Entity.OtpVerification;
import com.arts.Arts.Tracking.Repo.OtpVerificationRepository;
import com.arts.Arts.Tracking.Users.UserDTO.ValidateOtpRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SMSServiceImpl implements SMSService {

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new SecureRandom();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Override
    public Object sendSms(String phoneNumber, String message) throws Exception {
        try {
            Twilio.init(accountSid, authToken);
            String phoneNumber1 = "+91" + phoneNumber;
            return Message.creator(new PhoneNumber(phoneNumber1), new PhoneNumber(twilioPhoneNumber),
                    message).create();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getOtpCode(String phoneNumber) {
        String otp = String.format("%06d", random.nextInt(1000000));
        otpStorage.put(phoneNumber, otp);










//        OtpVerification otpSent = new OtpVerification();
//
//        OtpVerification existingPhoneNumber = otpVerificationRepository.findByMobileNumber(phoneNumber);
//        if (existingPhoneNumber==null){
//
//            otpSent.setOtp(passwordEncoder.encode(otp));
//            otpSent.setUsed(false);
//
//            otpSent.setExpiryTime(LocalDateTime.now().plusMinutes(5));
//            otpSent.setMobileNumber(phoneNumber);
//            otpSent.setCreatedAt(LocalDateTime.now());
//            otpSent.setAttempts(1);
//            otpSent.setUpdatedAt(LocalDateTime.now());
//        } else {
//            otpSent.setOtp(passwordEncoder.encode(otp));
//            otpSent.setUsed(false);
//
//            otpSent.setExpiryTime(LocalDateTime.now().plusMinutes(5));
//            otpSent.setMobileNumber(phoneNumber);
//            otpSent.setCreatedAt(existingPhoneNumber.getCreatedAt());
//            otpSent.setAttempts(existingPhoneNumber.getAttempts()+1);
//            otpSent.setUpdatedAt(LocalDateTime.now());
//        }
//
//        otpVerificationRepository.save(otpSent);
        return otp;
    }

    @Override
    public boolean validateOtp(ValidateOtpRequest otp) {
        OtpVerification existingPhoneNumber = otpVerificationRepository.findByMobileNumber(otp.getMobile());
        return passwordEncoder.matches(otp.getOtp(), existingPhoneNumber.getOtp());
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }

}