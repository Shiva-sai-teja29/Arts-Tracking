package com.arts.Arts.Tracking.Users.UserDTO;

import com.arts.Arts.Tracking.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profileImage;
    private boolean accountNonLocked = true;
    private boolean emailVerified = false;
    private Boolean mobileVerified = false;
}
