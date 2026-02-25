package tn.esprit.clubservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Club_Registration (diagram: IdCR, Date_Inscription, Status, User_Id, Club_Id).
 * User is not implemented in this module; userId is stored as reference.
 */
@Entity
@Table(name = "club_registration")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    @JsonIgnore
    private Club club;

    /**
     * Transient field to receive Club_Id from JSON without interfering
     * with the JPA club relationship.
     */
    @Transient
    @JsonProperty("Club_Id")
    private Long clubIdInput;

    public ClubRegistration() {
    }

    public ClubRegistration(Long id, LocalDateTime dateInscription, String fullName, String email, String phoneNumber, String studentId, String yearOfStudy, String motivation, String skills, Boolean termsAccepted, Long userId, Club club) {
        this.id = id;
        this.dateInscription = dateInscription;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
        this.yearOfStudy = yearOfStudy;
        this.motivation = motivation;
        this.skills = skills;
        this.termsAccepted = termsAccepted;
        this.userId = userId;
        this.club = club;
    }

    /**
     * Returns the Club ID — from the club entity if loaded, or from the
     * transient input field (used during JSON deserialization).
     */
    public Long getClubId() {
        if (this.club != null) {
            return this.club.getId();
        }
        return this.clubIdInput;
    }

    public Long getClubIdInput() {
        return clubIdInput;
    }

    public void setClubIdInput(Long clubIdInput) {
        this.clubIdInput = clubIdInput;
    }

    @PrePersist
    protected void onCreate() {
        if (dateInscription == null) {
            dateInscription = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Club getClub() { return club; }
    public void setClub(Club club) { this.club = club; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(String yearOfStudy) { this.yearOfStudy = yearOfStudy; }
    public String getMotivation() { return motivation; }
    public void setMotivation(String motivation) { this.motivation = motivation; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public Boolean getTermsAccepted() { return termsAccepted; }
    public void setTermsAccepted(Boolean termsAccepted) { this.termsAccepted = termsAccepted; }

    // Builder manual implementation
    public static ClubRegistrationBuilder builder() {
        return new ClubRegistrationBuilder();
    }

    public static class ClubRegistrationBuilder {
        private Long id;
        private LocalDateTime dateInscription;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String studentId;
        private String yearOfStudy;
        private String motivation;
        private String skills;
        private Boolean termsAccepted;
        private Long userId;
        private Club club;

        public ClubRegistrationBuilder id(Long id) { this.id = id; return this; }
        public ClubRegistrationBuilder dateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; return this; }
        public ClubRegistrationBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public ClubRegistrationBuilder email(String email) { this.email = email; return this; }
        public ClubRegistrationBuilder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public ClubRegistrationBuilder studentId(String studentId) { this.studentId = studentId; return this; }
        public ClubRegistrationBuilder yearOfStudy(String yearOfStudy) { this.yearOfStudy = yearOfStudy; return this; }
        public ClubRegistrationBuilder motivation(String motivation) { this.motivation = motivation; return this; }
        public ClubRegistrationBuilder skills(String skills) { this.skills = skills; return this; }
        public ClubRegistrationBuilder termsAccepted(Boolean termsAccepted) { this.termsAccepted = termsAccepted; return this; }
        public ClubRegistrationBuilder userId(Long userId) { this.userId = userId; return this; }
        public ClubRegistrationBuilder club(Club club) { this.club = club; return this; }

        public ClubRegistration build() {
            return new ClubRegistration(id, dateInscription, fullName, email, phoneNumber, studentId, yearOfStudy, motivation, skills, termsAccepted, userId, club);
        }
    }
}

