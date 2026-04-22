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

import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.dto.ClubDTO;
import tn.esprit.clubservice.repository.ClubRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubService clubService;

    private Club testClub;
    private ClubDTO testClubDTO;

    @BeforeEach
    void setUp() {
        testClub = new Club();
        testClub.setId(1L);
        testClub.setName("Google Developer Student Club");
        testClub.setBudget(1000.0);

        testClubDTO = new ClubDTO();
        testClubDTO.setId(1L);
        testClubDTO.setName("Google Developer Student Club");
        testClubDTO.setBudget(1000.0);
    }

    @Test
    void testFindById_Found() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(testClub));

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

    @Test
    void testFindAll() {
        when(clubRepository.findAll()).thenReturn(java.util.Arrays.asList(testClub));
        List<ClubDTO> all = clubService.findAll();
        assertEquals(1, all.size());
        verify(clubRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(clubRepository.save(any(Club.class))).thenReturn(testClub);
        ClubDTO created = clubService.create(testClubDTO);
        assertEquals("Google Developer Student Club", created.getName());
        verify(clubRepository, times(1)).save(any(Club.class));
    }

    @Test
    void testUpdate() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(testClub));
        when(clubRepository.save(any(Club.class))).thenReturn(testClub);
        
        testClubDTO.setName("Updated Club");
        ClubDTO updated = clubService.update(1L, testClubDTO);
        
        assertEquals("Updated Club", updated.getName());
        verify(clubRepository, times(1)).save(any(Club.class));
    }

    @Test
    void testDeleteById() {
        when(clubRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clubRepository).deleteById(1L);
        clubService.deleteById(1L);
        verify(clubRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByStatus() {
        when(clubRepository.findByStatus(any())).thenReturn(java.util.Arrays.asList(testClub));
        List<ClubDTO> clubs = clubService.findByStatus(Club.ClubStatus.ACTIVE);
        assertEquals(1, clubs.size());
    }

    @Test
    void testFindByCategory() {
        when(clubRepository.findByCategory(any())).thenReturn(java.util.Arrays.asList(testClub));
        List<ClubDTO> clubs = clubService.findByCategory(Club.ClubCategory.TECHNOLOGY);
        assertEquals(1, clubs.size());
    }

    @Test
    void testSearchByName() {
        when(clubRepository.findByNameContainingIgnoreCase("Google")).thenReturn(java.util.Arrays.asList(testClub));
        List<ClubDTO> clubs = clubService.searchByName("Google");
        assertEquals(1, clubs.size());
    }
}
