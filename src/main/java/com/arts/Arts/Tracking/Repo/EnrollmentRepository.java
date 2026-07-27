package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.Academy;
import com.arts.Arts.Tracking.Entity.Branch;
import com.arts.Arts.Tracking.Entity.Enrollment;
import com.arts.Arts.Tracking.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends BaseRepository<Enrollment>{

    List<Enrollment> findByCoach(User coach);

    List<Enrollment> findByStudent(User student);

    List<Enrollment> findByAcademy(Academy academy);

    List<Enrollment> findByBranch(Branch branch);

    @Query("select count(distinct e.student) from Enrollment e where e.academy.academyName=:academy")
    long countByAcademy(Academy academy);

    @Query("select count(distinct e.student) from Enrollment e where e.branch.branchName=:branch")
    long countByBranch(Branch branch);

    @Query("select count(distinct e.student) from Enrollment e where e.coach.username=:coach")
    long countByCoach(User coach);
}
