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
}
