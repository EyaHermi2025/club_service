package tn.esprit.clubservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.clubservice.repository.ClubRegistrationRepository;

@ExtendWith(MockitoExtension.class)
public class PopularityJobServiceTest {

    @Mock
    private ClubRegistrationRepository registrationRepository;

    @Mock
    private PopularityWebSocketHandler webSocketHandler;

    @InjectMocks
    private PopularityJobService popularityJobService;

    @Test
    void testCalculatePopularity_NoResults() {
        when(registrationRepository.countRegistrationsByClubForDate(any(LocalDateTime.class))).thenReturn(new ArrayList<>());
        popularityJobService.calculatePopularity();
        assertEquals("No registrations today", popularityJobService.getMostPopularClub());
    }

    @Test
    void testCalculatePopularity_WithResults() {
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Tech Club", 5L});

        when(registrationRepository.countRegistrationsByClubForDate(any(LocalDateTime.class))).thenReturn(results);
        doNothing().when(webSocketHandler).broadcast(anyString());

        popularityJobService.calculatePopularity();

        String popular = popularityJobService.getMostPopularClub();
        assertTrue(popular.contains("Tech Club"));
        assertTrue(popular.contains("5"));
        verify(webSocketHandler, times(1)).broadcast(anyString());
    }
}
