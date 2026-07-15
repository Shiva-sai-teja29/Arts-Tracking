package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.Role;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {
    Optional<Role> findByName(String role);
}
