package tn.esprit.clubservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.clubservice.dto.ClubRegistrationDTO;
import tn.esprit.clubservice.service.ClubRegistrationService;

import java.util.List;

@RestController
@RequestMapping("/api/club-registrations")
@CrossOrigin(origins = "*")
public class ClubRegistrationController {

    private final ClubRegistrationService registrationService;

    public ClubRegistrationController(ClubRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<ClubRegistrationDTO>> getAll() {
        return ResponseEntity.ok(registrationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubRegistrationDTO> getById(@PathVariable Long id) {
        return registrationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClubRegistrationDTO> create(@Valid @RequestBody ClubRegistrationDTO registrationDTO) {
        ClubRegistrationDTO created = registrationService.create(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubRegistrationDTO> update(@PathVariable Long id, @Valid @RequestBody ClubRegistrationDTO registrationDTO) {
        ClubRegistrationDTO updated = registrationService.update(id, registrationDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registrationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<ClubRegistrationDTO>> getByClubId(@PathVariable Long clubId) {
        return ResponseEntity.ok(registrationService.findByClubId(clubId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClubRegistrationDTO>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(registrationService.findByUserId(userId));
    }
}
