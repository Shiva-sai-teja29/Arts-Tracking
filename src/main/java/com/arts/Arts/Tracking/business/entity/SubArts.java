package com.arts.Arts.Tracking.business.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"academies", "arts"})
@ToString(exclude = {"academies", "arts"})
public class SubArts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subArtId;
    private String subArtName;
    private String description;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_Id")
    private Arts arts;

    @JsonBackReference("academy-subart")
    @ManyToMany(mappedBy = "subArts")
    private Set<Academy> academies;

}
