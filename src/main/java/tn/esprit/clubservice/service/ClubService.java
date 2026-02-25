package tn.esprit.clubservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.clubservice.entity.Club;
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

    @Transactional(readOnly = true)
    public List<Club> findAll() {
        return clubRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Club> findById(Long id) {
        return clubRepository.findById(id);
    }

    @Transactional
    public Club create(Club club) {
        return clubRepository.save(club);
    }

    @Transactional
    public Club update(Long id, Club clubDetails) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", id));
        club.setName(clubDetails.getName());
        club.setStatus(clubDetails.getStatus());
        club.setCategory(clubDetails.getCategory());
        club.setEmailContact(clubDetails.getEmailContact());
        club.setBudget(clubDetails.getBudget());
        return clubRepository.save(club);
    }

    @Transactional
    public void deductBudget(Long id, Double amount) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", id));
        if (club.getBudget() < amount) {
            throw new RuntimeException("Insufficient budget for " + club.getName());
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
    public List<Club> findByStatus(Club.ClubStatus status) {
        return clubRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Club> findByCategory(Club.ClubCategory category) {
        return clubRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Club> searchByName(String name) {
        return clubRepository.findByNameContainingIgnoreCase(name);
    }
}
