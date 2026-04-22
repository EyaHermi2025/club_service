package tn.esprit.clubservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import tn.esprit.clubservice.dto.ClubRegistrationEvent;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendRegistrationEvent() {
        ClubRegistrationEvent event = new ClubRegistrationEvent("test@test.com", "Student", "Club");
        
        kafkaProducerService.sendRegistrationEvent(event);
        
        verify(kafkaTemplate, times(1)).send(anyString(), eq(event));
    }
}
