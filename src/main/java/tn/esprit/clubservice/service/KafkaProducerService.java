package tn.esprit.clubservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tn.esprit.clubservice.dto.ClubRegistrationEvent;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "registration-topic";

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRegistrationEvent(ClubRegistrationEvent event) {
        log.info("Sending club registration event to Kafka: {}", event);
        kafkaTemplate.send(TOPIC, event);
    }
}
