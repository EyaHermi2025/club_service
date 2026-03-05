package tn.esprit.clubservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.clubservice.entity.ClubRegistration;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClubRegistrationRepository extends JpaRepository<ClubRegistration, Long> {

    @Query("SELECT r.club.name, COUNT(r) FROM ClubRegistration r " +
            "WHERE r.dateInscription >= :startDate " +
            "GROUP BY r.club.name " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> countRegistrationsByClubForDate(@Param("startDate") LocalDateTime startDate);

    List<ClubRegistration> findByClub_Id(Long clubId);

    List<ClubRegistration> findByUserId(Long userId);

    boolean existsByClub_IdAndUserId(Long clubId, Long userId);
}
