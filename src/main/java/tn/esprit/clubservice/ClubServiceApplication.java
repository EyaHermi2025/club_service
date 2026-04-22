package tn.esprit.clubservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import tn.esprit.clubservice.repository.ClubRepository;
import tn.esprit.clubservice.entity.Club;
import tn.esprit.clubservice.entity.Club.ClubStatus;
import tn.esprit.clubservice.entity.Club.ClubCategory;
import java.time.LocalDateTime;

@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
@org.springframework.cloud.client.discovery.EnableDiscoveryClient
@EnableScheduling
public class ClubServiceApplication {

    // ✅ Logger ici au niveau de la classe
    private static final Logger logger = LoggerFactory.getLogger(ClubServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClubServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadMockData(ClubRepository clubRepository) {
        return args -> {
            if (clubRepository.count() == 0) {
                Club club1 = Club.builder()
                        .name("Tech Innovators Club")
                        .status(ClubStatus.ACTIVE)
                        .category(ClubCategory.TECHNOLOGY)
                        .emailContact("tech@school.com")
                        .budget(5000.0)
                        .creationDate(LocalDateTime.now())
                        .build();

                Club club2 = Club.builder()
                        .name("Art & Design Studio")
                        .status(ClubStatus.ACTIVE)
                        .category(ClubCategory.ARTS)
                        .emailContact("art@school.com")
                        .budget(3000.0)
                        .creationDate(LocalDateTime.now())
                        .build();

                Club club3 = Club.builder()
                        .name("Global Sports Academy")
                        .status(ClubStatus.ACTIVE)
                        .category(ClubCategory.SPORTS)
                        .emailContact("sports@school.com")
                        .budget(8000.0)
                        .creationDate(LocalDateTime.now())
                        .build();

                clubRepository.save(club1);
                clubRepository.save(club2);
                clubRepository.save(club3);

                logger.info("✅ Mock clubs inserted into database successfully!");
            }
        };
    }
}