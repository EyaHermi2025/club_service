package tn.esprit.clubservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tn.esprit.clubservice.entity.Club.ClubStatus;
import tn.esprit.clubservice.entity.Club.ClubCategory;
import java.time.LocalDateTime;

public class ClubDTO {
    @JsonProperty("ID_Club")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("Status")
    private ClubStatus status;
    
    @JsonProperty("CreationDate")
    private LocalDateTime creationDate;
    
    @JsonProperty("Email_Contact")
    private String emailContact;
    
    @JsonProperty("Category")
    private ClubCategory category;
    
    @JsonProperty("budget")
    private Double budget;

    public ClubDTO() {
        // Default constructor required for JSON serialization
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ClubStatus getStatus() { return status; }
    public void setStatus(ClubStatus status) { this.status = status; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public String getEmailContact() { return emailContact; }
    public void setEmailContact(String emailContact) { this.emailContact = emailContact; }
    public ClubCategory getCategory() { return category; }
    public void setCategory(ClubCategory category) { this.category = category; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
}
