package tn.esprit.clubservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.entity.ClubRegistration;
import tn.esprit.clubservice.exception.ResourceNotFoundException;
import tn.esprit.clubservice.dto.ClubRegistrationEvent;
import tn.esprit.clubservice.repository.ClubRegistrationRepository;
import tn.esprit.clubservice.repository.ClubRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClubRegistrationService {

    private final ClubRegistrationRepository registrationRepository;
    private final ClubRepository clubRepository;
    private final KafkaProducerService kafkaProducerService;

    public ClubRegistrationService(ClubRegistrationRepository registrationRepository, ClubRepository clubRepository, KafkaProducerService kafkaProducerService) {
        this.registrationRepository = registrationRepository;
        this.clubRepository = clubRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Transactional(readOnly = true)
    public List<ClubRegistration> findAll() {
        return registrationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ClubRegistration> findById(Long id) {
        return registrationRepository.findById(id);
    }

    @Transactional
    public ClubRegistration create(ClubRegistration registration) {
        Long clubId = registration.getClubId();
        if (clubId == null) {
            throw new RuntimeException("Club_Id is required");
        }
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", clubId));
        registration.setClub(club);
        if (registration.getStatus() == null) {
            registration.setStatus("Pending");
        }
        ClubRegistration saved = registrationRepository.save(registration);


        ClubRegistrationEvent event = new ClubRegistrationEvent(
                saved.getEmail(),
                saved.getFullName(),
                club.getName()
        );
        kafkaProducerService.sendRegistrationEvent(event);

        return saved;
    }

    @Transactional
    public ClubRegistration update(Long id, ClubRegistration details) {
        ClubRegistration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClubRegistration", id));
        registration.setDateInscription(details.getDateInscription());
        registration.setUserId(details.getUserId());
        registration.setFullName(details.getFullName());
        registration.setEmail(details.getEmail());
        registration.setPhoneNumber(details.getPhoneNumber());
        registration.setStudentId(details.getStudentId());
        registration.setYearOfStudy(details.getYearOfStudy());
        registration.setMotivation(details.getMotivation());
        registration.setSkills(details.getSkills());
        registration.setTermsAccepted(details.getTermsAccepted());

        if (details.getClubId() != null) {
            Club club = clubRepository.findById(details.getClubId())
                    .orElseThrow(() -> new ResourceNotFoundException("Club", details.getClubId()));
            registration.setClub(club);
        }
        return registrationRepository.save(registration);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("ClubRegistration", id);
        }
        registrationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClubRegistration> findByClubId(Long clubId) {
        return registrationRepository.findByClub_Id(clubId);
    }

    @Transactional(readOnly = true)
    public List<ClubRegistration> findByUserId(Long userId) {
        return registrationRepository.findByUserId(userId);
    }
}
