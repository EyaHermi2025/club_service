package tn.esprit.clubservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.clubservice.repository.ClubRegistrationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PopularityJobService {

    private static final Logger log = LoggerFactory.getLogger(PopularityJobService.class);
    private final ClubRegistrationRepository registrationRepository;
    private final PopularityWebSocketHandler webSocketHandler;
    
    // Thread-safe storage for the most popular club name
    private final AtomicReference<String> mostPopularClub = new AtomicReference<>("No registrations today");

    public PopularityJobService(ClubRegistrationRepository registrationRepository, PopularityWebSocketHandler webSocketHandler) {
        this.registrationRepository = registrationRepository;
        this.webSocketHandler = webSocketHandler;
    }

    /**
     * Executes every 1 second to update the most popular club of the day.
     * The result is pushed via WebSocket for real-time updates.
     */
    @Scheduled(fixedRate = 1000)
    public void calculatePopularity() {
        log.info("Calculating most popular club... (last 24h window)");
        
        LocalDateTime startDate = LocalDateTime.now().minusHours(24);
        List<Object[]> results = registrationRepository.countRegistrationsByClubForDate(startDate);

        String newMessage = "No registrations today";
        if (results != null && !results.isEmpty()) {
            Object[] topResult = results.get(0);
            String clubName = (String) topResult[0];
            Long count = (Long) topResult[1];
            newMessage = clubName + " (with " + count + " new registrations!)";
        }

        String currentMessage = mostPopularClub.get();
        if (!newMessage.equals(currentMessage)) {
            mostPopularClub.set(newMessage);
            log.info("Popular club changed to: {}. Broadcasting to WebSocket...", newMessage);
            
            // Push the update to all connected clients
            webSocketHandler.broadcast(newMessage);
        }
    }

    public String getMostPopularClub() {
        return mostPopularClub.get();
    }
}
