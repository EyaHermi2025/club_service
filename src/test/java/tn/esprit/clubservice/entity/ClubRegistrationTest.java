package tn.esprit.clubservice.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClubRegistrationTest {

    @Test
    void testEntityBoilerplate() {
        Club club = Club.builder().id(1L).name("Club").build();
        
        ClubRegistration reg = ClubRegistration.builder()
                .id(1L)
                .fullName("Student")
                .email("student@test.com")
                .club(club)
                .build();
        
        assertEquals(1L, reg.getId());
        assertEquals("Student", reg.getFullName());
        assertEquals(1L, reg.getClubId());
        
        reg.setPhoneNumber("123456");
        assertEquals("123456", reg.getPhoneNumber());
    }

    @Test
    void testOnCreate() {
        ClubRegistration reg = new ClubRegistration();
        reg.onCreate();
        assertNotNull(reg.getDateInscription());
    }
}
