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
import tn.esprit.clubservice.repository.ClubRepository;

@ExtendWith(MockitoExtension.class)
class ClubServiceTest {

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

        Optional<Club> found = clubService.findById(1L);

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
        java.util.List<Club> all = clubService.findAll();
        assertEquals(1, all.size());
        verify(clubRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(clubRepository.save(any(Club.class))).thenReturn(testClub);
        Club created = clubService.create(testClub);
        assertEquals("Google Developer Student Club", created.getName());
        verify(clubRepository, times(1)).save(testClub);
    }

    @Test
    void testUpdate() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(testClub));
        when(clubRepository.save(any(Club.class))).thenReturn(testClub);
        
        testClub.setName("Updated Club");
        Club updated = clubService.update(1L, testClub);
        
        assertEquals("Updated Club", updated.getName());
        verify(clubRepository, times(1)).save(testClub);
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
        java.util.List<Club> clubs = clubService.findByStatus(Club.ClubStatus.ACTIVE);
        assertEquals(1, clubs.size());
    }

    @Test
    void testFindByCategory() {
        when(clubRepository.findByCategory(any())).thenReturn(java.util.Arrays.asList(testClub));
        java.util.List<Club> clubs = clubService.findByCategory(Club.ClubCategory.TECHNOLOGY);
        assertEquals(1, clubs.size());
    }

    @Test
    void testSearchByName() {
        when(clubRepository.findByNameContainingIgnoreCase("Google")).thenReturn(java.util.Arrays.asList(testClub));
        java.util.List<Club> clubs = clubService.searchByName("Google");
        assertEquals(1, clubs.size());
    }
}
