package tn.esprit.clubservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.http.MediaType;
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
    void testGetAllRegistrations() throws Exception {
        ClubRegistrationDTO dto = new ClubRegistrationDTO();
        dto.setId(1L);
        dto.setFullName("Student");
        
        when(registrationService.findAll()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/registrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Student"));
    }

    @Test
    void testGetRegistrationById() throws Exception {
        ClubRegistrationDTO dto = new ClubRegistrationDTO();
        dto.setId(1L);
        dto.setFullName("Student");

        when(registrationService.findById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/registrations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Student"));
    }

    @Test
    void testCreateRegistration() throws Exception {
        ClubRegistrationDTO dto = new ClubRegistrationDTO();
        dto.setId(1L);
        dto.setFullName("New Student");

        when(registrationService.create(any(ClubRegistrationDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/registrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\":\"New Student\", \"email\":\"student@test.com\", \"clubId\":1, \"userId\":123, \"phoneNumber\":\"123\", \"studentId\":\"S1\", \"yearOfStudy\":\"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("New Student"));
    }

    @Test
    void testUpdateRegistration() throws Exception {
        ClubRegistrationDTO dto = new ClubRegistrationDTO();
        dto.setFullName("Updated Student");

        when(registrationService.update(eq(1L), any(ClubRegistrationDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/registrations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\":\"Updated Student\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Student"));
    }

    @Test
    void testDeleteRegistration() throws Exception {
        mockMvc.perform(delete("/api/registrations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetByClubId() throws Exception {
        ClubRegistrationDTO dto = new ClubRegistrationDTO();
        when(registrationService.findByClubId(1L)).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/registrations/club/1"))
                .andExpect(status().isOk());
    }
}
