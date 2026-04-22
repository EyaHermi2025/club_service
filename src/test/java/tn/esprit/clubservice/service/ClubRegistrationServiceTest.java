package tn.esprit.clubservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.entity.ClubRegistration;
import tn.esprit.clubservice.repository.ClubRegistrationRepository;
import tn.esprit.clubservice.repository.ClubRepository;
import tn.esprit.clubservice.dto.ClubRegistrationEvent;

@ExtendWith(MockitoExtension.class)
public class ClubRegistrationServiceTest {

    @Mock
    private ClubRegistrationRepository registrationRepository;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private ClubRegistrationService registrationService;

    private Club testClub;
    private ClubRegistration testRegistration;

    @BeforeEach
    void setUp() {
        testClub = new Club();
        testClub.setId(1L);
        testClub.setName("Tech Club");

        testRegistration = new ClubRegistration();
        testRegistration.setId(1L);
        testRegistration.setClubId(1L);
        testRegistration.setEmail("student@mail.com");
        testRegistration.setFullName("John Doe");
    }

    @Test
    void testFindAll() {
        when(registrationRepository.findAll()).thenReturn(Arrays.asList(testRegistration));
        List<ClubRegistration> list = registrationService.findAll();
        assertEquals(1, list.size());
        verify(registrationRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Found() {
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(testRegistration));
        Optional<ClubRegistration> found = registrationService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getFullName());
    }

    @Test
    void testCreate_Success() {
        when(clubRepository.findById(1L)).thenReturn(Optional.of(testClub));
        when(registrationRepository.save(any(ClubRegistration.class))).thenReturn(testRegistration);
        doNothing().when(kafkaProducerService).sendRegistrationEvent(any(ClubRegistrationEvent.class));

        ClubRegistration created = registrationService.create(testRegistration);

        assertNotNull(created);
        assertEquals("Pending", created.getStatus());
        verify(kafkaProducerService, times(1)).sendRegistrationEvent(any());
        verify(registrationRepository, times(1)).save(any());
    }

    @Test
    void testCreate_MissingClubId() {
        testRegistration.setClubId(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.create(testRegistration);
        });
    }

    @Test
    void testUpdate_Success() {
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(testRegistration));
        when(registrationRepository.save(any(ClubRegistration.class))).thenReturn(testRegistration);

        ClubRegistration details = new ClubRegistration();
        details.setFullName("Updated Name");
        
        ClubRegistration updated = registrationService.update(1L, details);
        
        assertNotNull(updated);
        verify(registrationRepository, times(1)).save(any());
    }

    @Test
    void testDeleteById_Success() {
        when(registrationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(registrationRepository).deleteById(1L);

        registrationService.deleteById(1L);

        verify(registrationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByClubId() {
        when(registrationRepository.findByClub_Id(1L)).thenReturn(Arrays.asList(testRegistration));
        List<ClubRegistration> results = registrationService.findByClubId(1L);
        assertEquals(1, results.size());
    }

    @Test
    void testFindByUserId() {
        when(registrationRepository.findByUserId(10L)).thenReturn(Arrays.asList(testRegistration));
        List<ClubRegistration> results = registrationService.findByUserId(10L);
        assertEquals(1, results.size());
    }
}
