package tn.esprit.clubservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "club")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    private Double budget = 0.0;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("club")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<ClubRegistration> registrations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
