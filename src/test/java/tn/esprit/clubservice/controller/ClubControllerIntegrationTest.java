package tn.esprit.clubservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.service.ClubService;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubService clubService;

    @Test
    public void testGetAllClubs() throws Exception {
        Club club = new Club();
        club.setId(1L);
        club.setName("Test Club");

        when(clubService.findAll()).thenReturn(Arrays.asList(club));

        mockMvc.perform(get("/api/clubs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Club"));
    }

    @Test
    public void testGetClubById() throws Exception {
        Club club = new Club();
        club.setId(1L);
        club.setName("Specific Club");

        when(clubService.findById(1L)).thenReturn(java.util.Optional.of(club));

        mockMvc.perform(get("/api/clubs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Specific Club"));
    }

    @Test
    public void testDeleteClub() throws Exception {
        org.mockito.Mockito.doNothing().when(clubService).deleteById(1L);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/clubs/1"))
                .andExpect(status().isNoContent());
    }
}
