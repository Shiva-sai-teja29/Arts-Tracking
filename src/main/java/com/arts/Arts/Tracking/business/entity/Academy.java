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
@EqualsAndHashCode(exclude = {"subArts", "coaches"})
@ToString(exclude = {"subArts", "coaches"})
public class Academy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long academyId;
    private String academyName;
    private String address;
    private String contact;
    private String description;

    @JsonManagedReference("academy-subart")
    @ManyToMany
    @JoinTable(
            name = "academy_sub_art",
            joinColumns = @JoinColumn(name = "academy_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_art_id")
    )
    private Set<SubArts> subArts;

    @ManyToMany(mappedBy = "academies")
    @JsonBackReference("coach-academy")
    private Set<Coach> coaches;
}
