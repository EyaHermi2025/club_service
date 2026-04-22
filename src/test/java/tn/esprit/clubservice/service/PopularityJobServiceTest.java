package tn.esprit.clubservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.clubservice.repository.ClubRegistrationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PopularityJobServiceTest {

    @Mock
    private ClubRegistrationRepository registrationRepository;

    @Mock
    private PopularityWebSocketHandler webSocketHandler;

    @InjectMocks
    private PopularityJobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculatePopularity_NoRegistrations() {
        when(registrationRepository.countRegistrationsByClubForDate(any())).thenReturn(Collections.emptyList());
        
        jobService.calculatePopularity();
        
        assertEquals("No registrations today", jobService.getMostPopularClub());
        verify(webSocketHandler, never()).broadcast(anyString());
    }

    @Test
    void testCalculatePopularity_WithRegistrations() {
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Tech Club", 5L});
        
        when(registrationRepository.countRegistrationsByClubForDate(any())).thenReturn(results);
        
        jobService.calculatePopularity();
        
        assertEquals("Tech Club (with 5 new registrations!)", jobService.getMostPopularClub());
        verify(webSocketHandler, times(1)).broadcast("Tech Club (with 5 new registrations!)");
    }

    @Test
    void testCalculatePopularity_NoChange() {
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Tech Club", 5L});
        when(registrationRepository.countRegistrationsByClubForDate(any())).thenReturn(results);
        
        // First run
        jobService.calculatePopularity();
        
        // Second run with same data
        jobService.calculatePopularity();
        
        verify(webSocketHandler, times(1)).broadcast(anyString()); // Still only 1 broadcast
    }
}
