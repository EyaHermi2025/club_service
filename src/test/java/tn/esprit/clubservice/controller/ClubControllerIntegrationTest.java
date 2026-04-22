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
    public void testCreateClub() throws Exception {
        Club club = new Club();
        club.setId(1L);
        club.setName("Test Club");

        when(clubService.create(org.mockito.ArgumentMatchers.any(Club.class))).thenReturn(club);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/clubs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Club\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Club"));
    }

    @Test
    public void testUpdateClub() throws Exception {
        Club club = new Club();
        club.setName("Updated Club");

        when(clubService.update(org.mockito.ArgumentMatchers.eq(1L), org.mockito.ArgumentMatchers.any(Club.class))).thenReturn(club);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/clubs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Club\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Club"));
    }

    @Test
    public void testGetClubsByStatus() throws Exception {
        Club club = new Club();
        club.setName("Active Club");

        when(clubService.findByStatus(org.mockito.ArgumentMatchers.any())).thenReturn(Arrays.asList(club));

        mockMvc.perform(get("/api/clubs/status/ACTIVE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Active Club"));
    }

    @Test
    public void testGetClubsByCategory() throws Exception {
        Club club = new Club();
        club.setName("Tech Club");

        when(clubService.findByCategory(org.mockito.ArgumentMatchers.any())).thenReturn(Arrays.asList(club));

        mockMvc.perform(get("/api/clubs/category/TECHNOLOGY")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tech Club"));
    }

    @Test
    public void testSearchByName() throws Exception {
        Club club = new Club();
        club.setName("Search Club");

        when(clubService.searchByName(org.mockito.ArgumentMatchers.anyString())).thenReturn(Arrays.asList(club));

        mockMvc.perform(get("/api/clubs/search?name=Search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Search Club"));
    }

    @Test
    public void testDeductBudget() throws Exception {
        org.mockito.Mockito.doNothing().when(clubService).deductBudget(org.mockito.ArgumentMatchers.eq(1L), org.mockito.ArgumentMatchers.anyDouble());

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/clubs/1/deduct-budget?amount=50.0"))
                .andExpect(status().isOk());
    }
}
