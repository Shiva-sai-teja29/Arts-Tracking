package com.arts.Arts.Tracking.Users.UserDTO;

import com.arts.Arts.Tracking.Entity.Enrollment;
import com.arts.Arts.Tracking.Entity.User;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {

    private String branchName;
    private String address;
    private String contact;
    private String description;
    private List<CoachResponse> coaches;
    private List<Enrollment> enrollments;
}
