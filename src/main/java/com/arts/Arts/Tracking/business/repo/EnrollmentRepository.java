package com.arts.Arts.Tracking.business.repo;

import com.arts.Arts.Tracking.business.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
}
