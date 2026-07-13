package com.arts.Arts.Tracking.business.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne
    @JoinColumn(name = "academy_id")
    private Academy academy;

    @ManyToOne
    @JoinColumn(name = "sub_art_id")
    private SubArts subArt;

    private LocalDate joinDate;

    private LocalDate endDate;

    private String status; // ACTIVE, COMPLETED, DROPPED
}