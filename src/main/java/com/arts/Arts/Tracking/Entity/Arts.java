package com.arts.Arts.Tracking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Arts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artId;

    private String artName;

    private String description;

    @OneToMany(mappedBy = "art", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubArts> subArts;
}