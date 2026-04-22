package tn.esprit.clubservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.dto.ClubDTO;
import tn.esprit.clubservice.service.ClubService;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
@CrossOrigin(origins = "*")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<ClubDTO>> getAllClubs() {
        return ResponseEntity.ok(clubService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubDTO> getClubById(@PathVariable("id") Long id) {
        return clubService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClubDTO> createClub(@Valid @RequestBody ClubDTO clubDTO) {
        ClubDTO created = clubService.create(clubDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubDTO> updateClub(@PathVariable("id") Long id, @Valid @RequestBody ClubDTO clubDTO) {
        ClubDTO updated = clubService.update(id, clubDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable("id") Long id) {
        clubService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClubDTO>> getClubsByStatus(@PathVariable("status") Club.ClubStatus status) {
        return ResponseEntity.ok(clubService.findByStatus(status));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ClubDTO>> getClubsByCategory(@PathVariable("category") Club.ClubCategory category) {
        return ResponseEntity.ok(clubService.findByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClubDTO>> searchByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(clubService.searchByName(name));
    }

    @PostMapping("/{id}/deduct-budget")
    public ResponseEntity<Void> deductBudget(@PathVariable("id") Long id, @RequestParam("amount") Double amount) {
        clubService.deductBudget(id, amount);
        return ResponseEntity.ok().build();
    }
}
