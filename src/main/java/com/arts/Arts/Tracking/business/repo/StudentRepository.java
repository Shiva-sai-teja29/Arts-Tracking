package com.arts.Arts.Tracking.business.repo;

import com.arts.Arts.Tracking.business.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    //List<Student> findByCoachId(Long coachId);

    Student findByStudentName(String student);
}
