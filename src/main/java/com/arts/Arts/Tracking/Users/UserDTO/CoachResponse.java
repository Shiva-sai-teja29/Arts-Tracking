package com.arts.Arts.Tracking.Users.UserDTO;

import com.arts.Arts.Tracking.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoachResponse {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profileImage;
    private boolean accountNonLocked = true;
    private boolean emailVerified = false;
    private Boolean mobileVerified = false;

    public CoachResponse(User user) {
    }
}
