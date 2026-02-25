package tn.esprit.clubservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Club entity (diagram: ID_Club, name, Status, CreationDate, Email_Contact, Category).
 */
@Entity
@Table(name = "club")
public class Club {

    public enum ClubStatus {
        ACTIVE, INACTIVE, PENDING, SUSPENDED
    }

    public enum ClubCategory {
        ACADEMY, SPORTS, ARTS, CULTURAL, SOCIAL, TECHNOLOGY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("ID_Club")
    private Long id;

    @NotBlank(message = "Club name is required")
    @Column(nullable = false)
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("Status")
    private ClubStatus status;

    @JsonProperty("CreationDate")
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Email(message = "Invalid email format")
    @Column(name = "email_contact")
    @JsonProperty("Email_Contact")
    private String emailContact;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("Category")
    private ClubCategory category;

    @Column(name = "budget")
    @JsonProperty("budget")
    private Double budget = 0.0;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("club")
    private List<ClubRegistration> registrations = new ArrayList<>();

    public Club() {
    }

    public Club(Long id, String name, ClubStatus status, LocalDateTime creationDate, String emailContact, ClubCategory category, Double budget, List<ClubRegistration> registrations) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.creationDate = creationDate;
        this.emailContact = emailContact;
        this.category = category;
        this.budget = budget;
        this.registrations = registrations;
    }

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
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
    public List<ClubRegistration> getRegistrations() { return registrations; }
    public void setRegistrations(List<ClubRegistration> registrations) { this.registrations = registrations; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    // Builder pattern (manual implementation for compatibility)
    public static ClubBuilder builder() {
        return new ClubBuilder();
    }

    public static class ClubBuilder {
        private Long id;
        private String name;
        private ClubStatus status;
        private LocalDateTime creationDate;
        private String emailContact;
        private ClubCategory category;
        private Double budget;

        public ClubBuilder id(Long id) { this.id = id; return this; }
        public ClubBuilder name(String name) { this.name = name; return this; }
        public ClubBuilder status(ClubStatus status) { this.status = status; return this; }
        public ClubBuilder creationDate(LocalDateTime creationDate) { this.creationDate = creationDate; return this; }
        public ClubBuilder emailContact(String emailContact) { this.emailContact = emailContact; return this; }
        public ClubBuilder category(ClubCategory category) { this.category = category; return this; }
        public ClubBuilder budget(Double budget) { this.budget = budget; return this; }

        public Club build() {
            Club club = new Club();
            club.setId(id);
            club.setName(name);
            club.setStatus(status);
            club.setCreationDate(creationDate);
            club.setEmailContact(emailContact);
            club.setCategory(category);
            club.setBudget(budget);
            return club;
        }
    }
}
