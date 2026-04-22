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
        LocalDateTime now = LocalDateTime.now();
        ClubDTO dto1 = ClubDTO.builder()
                .id(1L).name("Name").status(ClubStatus.ACTIVE).creationDate(now)
                .emailContact("t@t.com").category(ClubCategory.TECHNOLOGY).budget(100.0).build();
        
        ClubDTO dto2 = ClubDTO.builder()
                .id(1L).name("Name").status(ClubStatus.ACTIVE).creationDate(now)
                .emailContact("t@t.com").category(ClubCategory.TECHNOLOGY).budget(100.0).build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
        assertNotEquals(dto1, new Object());
        
        ClubDTO empty = new ClubDTO();
        empty.setId(2L);
        assertEquals(2L, empty.getId());
    }

    @Test
    void testClubRegistrationDTO() {
        ClubRegistrationDTO dto1 = ClubRegistrationDTO.builder()
                .id(1L).fullName("N").email("e").phoneNumber("p").studentId("s").yearOfStudy("y")
                .motivation("m").skills("s").termsAccepted(true).userId(1L).status("P").clubId(1L).build();
        
        ClubRegistrationDTO dto2 = ClubRegistrationDTO.builder()
                .id(1L).fullName("N").email("e").phoneNumber("p").studentId("s").yearOfStudy("y")
                .motivation("m").skills("s").termsAccepted(true).userId(1L).status("P").clubId(1L).build();

        assertEquals(dto1, dto2);
        assertNotNull(dto1.toString());
    }

    @Test
    void testClubEntity() {
        Club c1 = Club.builder().id(1L).name("A").status(ClubStatus.ACTIVE).category(ClubCategory.ARTS).budget(1.0).build();
        Club c2 = Club.builder().id(1L).name("A").status(ClubStatus.ACTIVE).category(ClubCategory.ARTS).budget(1.0).build();
        
        assertEquals(c1, c2);
        c1.onCreate();
        assertNotNull(c1.getCreationDate());
    }

    @Test
    void testClubRegistrationEntity() {
        Club club = new Club();
        club.setId(10L);
        ClubRegistration r1 = ClubRegistration.builder().id(1L).fullName("S").club(club).build();
        ClubRegistration r2 = ClubRegistration.builder().id(1L).fullName("S").club(club).build();

        assertEquals(r1, r2);
        assertEquals(10L, r1.getClubId());
        r1.onCreate();
        assertNotNull(r1.getDateInscription());
    }

    @Test
    void testClubRegistrationEvent() {
        ClubRegistrationEvent e1 = new ClubRegistrationEvent("e", "f", "c");
        ClubRegistrationEvent e2 = new ClubRegistrationEvent("e", "f", "c");
        assertEquals(e1.getEmail(), e2.getEmail());
        assertNotNull(e1.toString());
        
        ClubRegistrationEvent empty = new ClubRegistrationEvent();
        empty.setEmail("test");
        assertEquals("test", empty.getEmail());
    }
}
