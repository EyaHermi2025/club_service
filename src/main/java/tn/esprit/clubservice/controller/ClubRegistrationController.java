package tn.esprit.clubservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.clubservice.entity.ClubRegistration;
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
    public ResponseEntity<List<ClubRegistration>> getAll() {
        return ResponseEntity.ok(registrationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubRegistration> getById(@PathVariable Long id) {
        return registrationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClubRegistration> create(@Valid @RequestBody ClubRegistration registration) {
        ClubRegistration created = registrationService.create(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubRegistration> update(@PathVariable Long id, @Valid @RequestBody ClubRegistration registration) {
        ClubRegistration updated = registrationService.update(id, registration);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registrationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<ClubRegistration>> getByClubId(@PathVariable Long clubId) {
        return ResponseEntity.ok(registrationService.findByClubId(clubId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClubRegistration>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(registrationService.findByUserId(userId));
    }
}
