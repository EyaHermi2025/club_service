package tn.esprit.clubservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.entity.ClubRegistration;
import tn.esprit.clubservice.dto.ClubRegistrationDTO;
import tn.esprit.clubservice.exception.ResourceNotFoundException;
import tn.esprit.clubservice.exception.ClubRegistrationValidationException;
import tn.esprit.clubservice.dto.ClubRegistrationEvent;
import tn.esprit.clubservice.repository.ClubRegistrationRepository;
import tn.esprit.clubservice.repository.ClubRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private ClubRegistrationDTO toDTO(ClubRegistration reg) {
        ClubRegistrationDTO dto = new ClubRegistrationDTO();
        dto.setId(reg.getId());
        dto.setDateInscription(reg.getDateInscription());
        dto.setFullName(reg.getFullName());
        dto.setEmail(reg.getEmail());
        dto.setPhoneNumber(reg.getPhoneNumber());
        dto.setStudentId(reg.getStudentId());
        dto.setYearOfStudy(reg.getYearOfStudy());
        dto.setMotivation(reg.getMotivation());
        dto.setSkills(reg.getSkills());
        dto.setTermsAccepted(reg.getTermsAccepted());
        dto.setUserId(reg.getUserId());
        dto.setStatus(reg.getStatus());
        if (reg.getClub() != null) {
            dto.setClubId(reg.getClub().getId());
        }
        return dto;
    }

    private ClubRegistration toEntity(ClubRegistrationDTO dto) {
        ClubRegistration reg = new ClubRegistration();
        reg.setId(dto.getId());
        reg.setDateInscription(dto.getDateInscription());
        reg.setFullName(dto.getFullName());
        reg.setEmail(dto.getEmail());
        reg.setPhoneNumber(dto.getPhoneNumber());
        reg.setStudentId(dto.getStudentId());
        reg.setYearOfStudy(dto.getYearOfStudy());
        reg.setMotivation(dto.getMotivation());
        reg.setSkills(dto.getSkills());
        reg.setTermsAccepted(dto.getTermsAccepted());
        reg.setUserId(dto.getUserId());
        reg.setStatus(dto.getStatus());
        return reg;
    }

    @Transactional(readOnly = true)
    public List<ClubRegistrationDTO> findAll() {
        return registrationRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ClubRegistrationDTO> findById(Long id) {
        return registrationRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public ClubRegistrationDTO create(ClubRegistrationDTO registrationDTO) {
        Long clubId = registrationDTO.getClubId();
        if (clubId == null) {
            throw new ClubRegistrationValidationException("Club_Id is required");
        }
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", clubId));
        
        ClubRegistration registration = toEntity(registrationDTO);
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

        return toDTO(saved);
    }

    @Transactional
    public ClubRegistrationDTO update(Long id, ClubRegistrationDTO details) {
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
        return toDTO(registrationRepository.save(registration));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("ClubRegistration", id);
        }
        registrationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClubRegistrationDTO> findByClubId(Long clubId) {
        return registrationRepository.findByClub_Id(clubId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClubRegistrationDTO> findByUserId(Long userId) {
        return registrationRepository.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }
}
