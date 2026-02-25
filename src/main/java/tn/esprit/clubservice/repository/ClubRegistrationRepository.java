package tn.esprit.clubservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.clubservice.entity.ClubRegistration;

import java.util.List;

@Repository
public interface ClubRegistrationRepository extends JpaRepository<ClubRegistration, Long> {

    List<ClubRegistration> findByClub_Id(Long clubId);

    List<ClubRegistration> findByUserId(Long userId);

    boolean existsByClub_IdAndUserId(Long clubId, Long userId);
}
