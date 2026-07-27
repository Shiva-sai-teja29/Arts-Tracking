package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.Branch;
import com.arts.Arts.Tracking.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends BaseRepository<Branch>{
    List<Branch> findByCoaches(User target);

    //List<Branch> findByStudents(User target);

    @Query("select distinct e.branch from Enrollment e where e.student = :student ")
    List<Branch> findBranchesByStudent(@Param("student") User student);

    @Query("select distinct b from Branch b where b.academy.academyName= :academy")
    List<Branch> findByAcademy(@Param("academy") String academy);

    Branch findByBranchName(String categoryName);
}
