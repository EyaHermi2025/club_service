package tn.esprit.clubservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubRegistrationDTO {
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
    private String status;
    private Long clubId;
}
