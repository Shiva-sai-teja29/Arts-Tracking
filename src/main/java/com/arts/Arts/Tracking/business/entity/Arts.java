package com.arts.Arts.Tracking.business.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class Arts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artId;
    private String artName;
    private String description;

    @OneToMany(mappedBy = "arts",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JsonManagedReference
    private List<SubArts> subArts;
}
