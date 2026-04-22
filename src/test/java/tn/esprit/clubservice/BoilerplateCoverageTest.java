package tn.esprit.clubservice;

import org.junit.jupiter.api.Test;
import tn.esprit.clubservice.dto.*;
import tn.esprit.clubservice.entity.*;
import tn.esprit.clubservice.entity.Club.ClubCategory;
import tn.esprit.clubservice.entity.Club.ClubStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Specifically designed to ensure 100% coverage on all boilerplate code 
 * (DTOs, Entities, Builders) introduced in the current refactoring.
 */
class BoilerplateCoverageTest {

    @Test
    void testClubDTO() {
        ClubDTO dto = ClubDTO.builder()
                .id(1L)
                .name("Name")
                .status(ClubStatus.ACTIVE)
                .creationDate(LocalDateTime.now())
                .emailContact("test@test.com")
                .category(ClubCategory.TECHNOLOGY)
                .budget(100.0)
                .build();
        
        assertNotNull(dto.getName());
        assertNotNull(dto.toString());
        assertEquals(dto, dto);
        assertNotEquals(dto, new Object());
        
        ClubDTO dto2 = new ClubDTO();
        dto2.setId(1L);
        assertEquals(1L, dto2.getId());
    }

    @Test
    void testClubRegistrationDTO() {
        ClubRegistrationDTO dto = ClubRegistrationDTO.builder()
                .id(1L)
                .fullName("Full Name")
                .email("a@b.com")
                .phoneNumber("123")
                .studentId("S1")
                .yearOfStudy("Y1")
                .motivation("M")
                .skills("S")
                .termsAccepted(true)
                .userId(1L)
                .status("Pending")
                .clubId(1L)
                .dateInscription(LocalDateTime.now())
                .build();
        
        assertNotNull(dto.toString());
        assertEquals(dto, dto);
        
        ClubRegistrationDTO dto2 = new ClubRegistrationDTO();
        dto2.setFullName("Name");
        assertEquals("Name", dto2.getFullName());
    }

    @Test
    void testClubEntity() {
        Club club = Club.builder()
                .id(1L)
                .name("Club")
                .status(ClubStatus.ACTIVE)
                .category(ClubCategory.ARTS)
                .budget(500.0)
                .registrations(new ArrayList<>())
                .build();
        
        assertNotNull(club.toString());
        assertEquals(club, club);
        
        Club club2 = new Club();
        club2.onCreate();
        assertNotNull(club2.getCreationDate());
    }

    @Test
    void testClubRegistrationEntity() {
        Club club = new Club();
        club.setId(10L);
        
        ClubRegistration reg = ClubRegistration.builder()
                .id(1L)
                .fullName("Student")
                .email("s@t.com")
                .phoneNumber("123")
                .studentId("S1")
                .yearOfStudy("1")
                .motivation("M")
                .skills("S")
                .termsAccepted(true)
                .userId(1L)
                .status("Active")
                .club(club)
                .clubIdInput(10L)
                .build();
        
        assertEquals(10L, reg.getClubId());
        assertNotNull(reg.toString());
        
        ClubRegistration reg2 = new ClubRegistration();
        reg2.setClubIdInput(20L);
        assertEquals(20L, reg2.getClubId());
        reg2.onCreate();
        assertNotNull(reg2.getDateInscription());
    }

    @Test
    void testClubRegistrationEvent() {
        ClubRegistrationEvent event = new ClubRegistrationEvent("e@e.com", "name", "club");
        assertEquals("e@e.com", event.getEmail());
        assertEquals("name", event.getFullName());
        assertEquals("club", event.getClubName());
        
        ClubRegistrationEvent empty = new ClubRegistrationEvent();
        empty.setClubName("C");
        assertEquals("C", empty.getClubName());
    }
}
