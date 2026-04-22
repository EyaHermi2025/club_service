package tn.esprit.clubservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import tn.esprit.clubservice.dto.ClubRegistrationDTO;
import tn.esprit.clubservice.service.ClubRegistrationService;

@SpringBootTest
@AutoConfigureMockMvc
class ClubRegistrationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubRegistrationService registrationService;

    @Test
    void testGetAll() throws Exception {
        ClubRegistrationDTO reg = new ClubRegistrationDTO();
        reg.setId(1L);
        reg.setFullName("Test User");

        when(registrationService.findAll()).thenReturn(Arrays.asList(reg));

        mockMvc.perform(get("/api/club-registrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test User"));
    }

    @Test
    void testGetById() throws Exception {
        ClubRegistrationDTO reg = new ClubRegistrationDTO();
        reg.setId(1L);
        reg.setFullName("Specific User");

        when(registrationService.findById(1L)).thenReturn(Optional.of(reg));

        mockMvc.perform(get("/api/club-registrations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Specific User"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/club-registrations/1"))
                .andExpect(status().isNoContent());
    }
}
