package tn.esprit.clubservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.clubservice.entity.Club;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByStatus(Club.ClubStatus status);

    List<Club> findByCategory(Club.ClubCategory category);

    List<Club> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
