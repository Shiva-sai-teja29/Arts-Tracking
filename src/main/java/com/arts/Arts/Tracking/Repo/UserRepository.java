package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.User;
import com.arts.Arts.Tracking.Security.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<UserInfo> findByEmail(String username);

    Optional<User> findByUsername(String username);
}
