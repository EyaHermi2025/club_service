package tn.esprit.clubservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "club_registration")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cr")
    @JsonProperty("IdCR")
    private Long id;

    @JsonProperty("Date_Inscription")
    @Column(name = "date_inscription", nullable = false)
    private LocalDateTime dateInscription;

    @JsonProperty("FullName")
    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @JsonProperty("Email")
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @JsonProperty("Phone")
    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @JsonProperty("StudentId")
    @NotBlank
    @Column(name = "student_id", nullable = false)
    private String studentId;

    @JsonProperty("YearOfStudy")
    @NotBlank
    @Column(name = "year_of_study", nullable = false)
    private String yearOfStudy;

    @JsonProperty("Motivation")
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String motivation;

    @JsonProperty("Skills")
    @Column(columnDefinition = "TEXT")
    private String skills;

    @JsonProperty("TermsAccepted")
    @Column(name = "terms_accepted")
    private Boolean termsAccepted;

    @JsonProperty("User_Id")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonProperty("Status")
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Club club;

    @Transient
    @JsonProperty("Club_Id")
    private Long clubIdInput;

    @PrePersist
    public void onCreate() {
        if (dateInscription == null) {
            dateInscription = LocalDateTime.now();
        }
    }

    public Long getClubId() {
        if (this.club != null) {
            return this.club.getId();
        }
        return this.clubIdInput;
    }
}
