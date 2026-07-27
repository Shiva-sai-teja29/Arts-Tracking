package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByEmail(String username);

    Optional<User> findByUsername(String username);

    @Query("select distinct u from User u join u.roles r where r.name='ROLE_STUDENT'")
    List<User> findAllStudents();

    @Query("select distinct u from User u join u.roles r where r.name='ROLE_COACH'")
    List<User> findAllCoaches();

    @Query("select distinct u from User u join u.roles r join u.academies a where r.name='ROLE_COACH' and a.academyName=:categoryName")
    List<User> findAllCoachesWithAcademy(String categoryName);

    @Query("select distinct u from User u join u.roles r join u.branches b where r.name='ROLE_COACH' and b.branchName=:categoryName")
    List<User> findAllCoachesWithBranch(@Param("categoryName") String categoryName);

    //User findByUser(User user);
}
