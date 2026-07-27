package com.arts.Arts.Tracking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubArts extends BaseEntity{

    private String subArtName;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id")
    private Arts art;

    @ManyToMany(mappedBy = "subArts")
    private Set<Academy> academies;

    @OneToMany(mappedBy = "subArt")
    private List<Enrollment> enrollments;
}