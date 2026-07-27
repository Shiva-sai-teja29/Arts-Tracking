package com.arts.Arts.Tracking.Users.UserDTO;

import com.arts.Arts.Tracking.Entity.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String profileImage;
    private boolean accountNonLocked = true;
    private boolean emailVerified = false;
    private Set<Role> roles = new HashSet<>();
    private Boolean mobileVerified = false;
}
