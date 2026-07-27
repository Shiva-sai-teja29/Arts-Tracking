package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.OtpVerification;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpVerificationRepository extends BaseRepository<OtpVerification> {
    OtpVerification findByMobileNumber(String phoneNumber);
}
