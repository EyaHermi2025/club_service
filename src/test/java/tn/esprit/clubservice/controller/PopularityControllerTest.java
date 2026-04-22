package tn.esprit.clubservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import tn.esprit.clubservice.service.PopularityJobService;

@SpringBootTest
@AutoConfigureMockMvc
public class PopularityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PopularityJobService popularityJobService;

    @Test
    public void testGetTopClub() throws Exception {
        when(popularityJobService.getMostPopularClub()).thenReturn("Popular Club (5 registrations)");

        mockMvc.perform(get("/api/clubs/popularity/top"))
                .andExpect(status().isOk())
                .andExpect(content().string("Popular Club (5 registrations)"));
    }
}
