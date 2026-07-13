package com.arts.Arts.Tracking.business.repo;

import com.arts.Arts.Tracking.business.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach,Long> {

    @Query("SELECT c FROM Coach c JOIN c.academy a WHERE a.academyId = :id")
    List<Coach> findByAcademyId(Long academyId);

    Optional<Coach> findByCoachName(String coach);
}
