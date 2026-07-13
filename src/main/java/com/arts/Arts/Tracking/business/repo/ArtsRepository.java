package com.arts.Arts.Tracking.business.repo;

import com.arts.Arts.Tracking.business.entity.Arts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtsRepository extends JpaRepository<Arts, Long> {

    Optional<Arts> findByArtName(String art);
}
