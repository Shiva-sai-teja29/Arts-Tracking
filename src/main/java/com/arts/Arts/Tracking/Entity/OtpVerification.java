package com.arts.Arts.Tracking.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verification")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OtpVerification extends BaseEntity {

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private int attempts;
}