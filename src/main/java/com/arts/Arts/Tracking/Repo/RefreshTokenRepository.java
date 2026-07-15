package com.arts.Arts.Tracking.Repo;

import com.arts.Arts.Tracking.Entity.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshToken> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String refreshToken);
}
