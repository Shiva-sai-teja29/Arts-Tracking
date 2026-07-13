package com.arts.Arts.Tracking.business.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"academies", "enrollments"})
@ToString(exclude = {"academies", "enrollments"})
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coachId;
    private String coachName;
    private String area;
    private String contact;
    private String description;

    @JsonBackReference("coach-academy")
    @ManyToMany
    @JoinTable(
            name = "coach_academy",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "academy_id")
    )
    private Set<Academy> academies;

    @OneToMany(mappedBy = "coach")
    private List<Enrollment> enrollments;
}
