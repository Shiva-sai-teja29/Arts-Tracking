package com.arts.Arts.Tracking.business.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private String studentName;
    private String area;
    private String contact;
    private String description;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
}
