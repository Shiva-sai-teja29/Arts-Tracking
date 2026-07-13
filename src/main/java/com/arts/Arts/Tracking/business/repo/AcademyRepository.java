package com.arts.Arts.Tracking.business.repo;

import com.arts.Arts.Tracking.business.entity.Academy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyRepository extends JpaRepository<Academy,Long> {

    @Query("SELECT a FROM Academy a JOIN a.subArts s WHERE s.subArtId = :id")
    List<Academy> findBySubArtId(@Param("id") Long subArtId);

    Optional<Academy> findByAcademyName(String academy);
}
