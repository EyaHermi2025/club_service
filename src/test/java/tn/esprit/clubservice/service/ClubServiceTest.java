package tn.esprit.clubservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.clubservice.dto.ClubDTO;
import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.repository.ClubRepository;

@ExtendWith(MockitoExtension.class)
public class ClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubService clubService;

    private Club testClub;

    @BeforeEach
    void setUp() {
        testClub = new Club();
        testClub.setId(1L);
        testClub.setName("Google Developer Student Club");
        testClub.setBudget(1000.0);
    }

    @Test
    void testFindById_Found() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(testClub));

        // ✅ Optional<ClubDTO> à la place de Optional<Club>
        Optional<ClubDTO> found = clubService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Google Developer Student Club", found.get().getName());
        verify(clubRepository, times(1)).findById(1L);
    }

    @Test
    void testDeductBudget_Success() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(testClub));

        clubService.deductBudget(1L, 200.0);

        assertEquals(800.0, testClub.getBudget());
        verify(clubRepository, times(1)).save(testClub);
    }

    @Test
    void testDeductBudget_Insufficient() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(testClub));

        assertThrows(RuntimeException.class, () -> {
            clubService.deductBudget(1L, 1200.0);
        });
    }
}