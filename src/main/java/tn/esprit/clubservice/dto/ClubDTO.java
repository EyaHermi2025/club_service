package tn.esprit.clubservice.dto;

import tn.esprit.clubservice.entity.Club.ClubStatus;
import tn.esprit.clubservice.entity.Club.ClubCategory;
import java.time.LocalDateTime;

public class ClubDTO {
    private Long id;
    private String name;
    private ClubStatus status;
    private ClubCategory category;
    private String emailContact;
    private Double budget;
    private LocalDateTime creationDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ClubStatus getStatus() { return status; }
    public void setStatus(ClubStatus status) { this.status = status; }

    public ClubCategory getCategory() { return category; }
    public void setCategory(ClubCategory category) { this.category = category; }

    public String getEmailContact() { return emailContact; }
    public void setEmailContact(String emailContact) { this.emailContact = emailContact; }

    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
}