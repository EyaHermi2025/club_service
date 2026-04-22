package tn.esprit.clubservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tn.esprit.clubservice.entity.Club.ClubStatus;
import tn.esprit.clubservice.entity.Club.ClubCategory;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
