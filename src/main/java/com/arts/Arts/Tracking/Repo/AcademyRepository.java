package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.Academy;
import com.arts.Arts.Tracking.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyRepository extends BaseRepository<Academy> {
    Optional<Academy> findByHeadCoach(User headCoach);

    List<Academy> findByCoaches(User target);

    //List<Academy> findByStudents(User target);

    @Query("select distinct e.academy from Enrollment e where e.student = :student")
    List<Academy> findAcademiesByStudent(@Param("student") User student);

    Academy findByAcademyName(String categoryName);

    //User findByUserName(String categoryName);
}
