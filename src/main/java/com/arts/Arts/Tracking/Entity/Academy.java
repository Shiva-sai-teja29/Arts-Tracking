package com.arts.Arts.Tracking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Academy extends BaseEntity{

    private String academyName;

    private String address;

    private String contact;

    private String description;

    @ManyToMany
    private Set<SubArts> subArts;

    @ManyToMany
    @JoinTable(
            name = "academy_coaches",
            joinColumns = @JoinColumn(name = "academy_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id")
    )
    private Set<User> coaches;

    @ManyToOne
    private User headCoach;

    @OneToMany(mappedBy="academy")
    private List<Branch> branches;

    @OneToMany(mappedBy="academy")
    private List<Enrollment> enrollments;
}