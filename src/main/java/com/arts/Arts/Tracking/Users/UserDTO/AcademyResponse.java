package com.arts.Arts.Tracking.Users.UserDTO;

import com.arts.Arts.Tracking.Entity.Enrollment;
import com.arts.Arts.Tracking.Entity.SubArts;
import com.arts.Arts.Tracking.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademyResponse {

    private String academyName;
    private String address;
    private String contact;
    private String description;
    private Set<SubArts> subArts;
//    private List<User> students;
    private User headCoach;
}
