package tn.esprit.clubservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.dto.ClubDTO;
import tn.esprit.clubservice.exception.InsufficientBudgetException;
import tn.esprit.clubservice.exception.ResourceNotFoundException;
import tn.esprit.clubservice.repository.ClubRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    private ClubDTO toDTO(Club club) {
        ClubDTO dto = new ClubDTO();
        dto.setId(club.getId());
        dto.setName(club.getName());
        dto.setStatus(club.getStatus());
        dto.setCategory(club.getCategory());
        dto.setBudget(club.getBudget());
        dto.setEmailContact(club.getEmailContact());
        dto.setCreationDate(club.getCreationDate());
        return dto;
    }

    private Club toEntity(ClubDTO dto) {
        Club club = new Club();
        club.setId(dto.getId());
        club.setName(dto.getName());
        club.setStatus(dto.getStatus());
        club.setCategory(dto.getCategory());
        club.setBudget(dto.getBudget());
        club.setEmailContact(dto.getEmailContact());
        return club;
    }

    @Transactional(readOnly = true)
    public List<ClubDTO> findAll() {
        return clubRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public Optional<ClubDTO> findById(Long id) {
        return clubRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public ClubDTO create(ClubDTO clubDTO) {
        Club club = toEntity(clubDTO);
        return toDTO(clubRepository.save(club));
    }

    @Transactional
    public ClubDTO update(Long id, ClubDTO clubDTO) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", id));
        club.setName(clubDTO.getName());
        club.setStatus(clubDTO.getStatus());
        club.setCategory(clubDTO.getCategory());
        club.setEmailContact(clubDTO.getEmailContact());
        club.setBudget(clubDTO.getBudget());
        return toDTO(clubRepository.save(club));
    }

    @Transactional
    public void deductBudget(Long id, Double amount) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", id));
        if (club.getBudget() < amount) {
            throw new InsufficientBudgetException("Insufficient budget for " + club.getName());
        }
        club.setBudget(club.getBudget() - amount);
        clubRepository.save(club);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!clubRepository.existsById(id)) {
            throw new ResourceNotFoundException("Club", id);
        }
        clubRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClubDTO> findByStatus(Club.ClubStatus status) {
        return clubRepository.findByStatus(status).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<ClubDTO> findByCategory(Club.ClubCategory category) {
        return clubRepository.findByCategory(category).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<ClubDTO> searchByName(String name) {
        return clubRepository.findByNameContainingIgnoreCase(name).stream().map(this::toDTO).toList();
    }
}
