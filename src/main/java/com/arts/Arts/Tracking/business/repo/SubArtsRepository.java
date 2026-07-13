package com.arts.Arts.Tracking.business.repo;

import com.arts.Arts.Tracking.business.entity.SubArts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubArtsRepository extends JpaRepository<SubArts,Long> {

    Optional<SubArts> findBySubArtName(String subArt);

    @Query("Select sa FROM SubArts sa WHERE sa.arts.artId = ?1")
    List<SubArts> findByArtId(Long artId);
}
