package com.arts.Arts.Tracking.business.entity;

import com.arts.Arts.Tracking.business.dto.Contact;
import com.arts.Arts.Tracking.business.dto.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ArtsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artsUserId;
    private String userName;
    private int age;
    private String area;
    private List<Contact> contact;
    private String qualification;
    private String profession;
    private List<Role> roles;
    private String dpImageId;
    private List<Post> posts;

    @OneToMany(mappedBy = "artUser")
    private List<Enrollment> enrollments;
}
