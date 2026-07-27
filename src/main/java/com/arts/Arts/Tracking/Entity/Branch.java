package com.arts.Arts.Tracking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "branch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends BaseEntity{

    private String branchName;

    private String address;

    private String contact;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "branch_coaches",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id")
    )
    private Set<User> coaches;

    @OneToMany(mappedBy = "branch")
    private List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name="academy_id")
    private Academy academy;
}
