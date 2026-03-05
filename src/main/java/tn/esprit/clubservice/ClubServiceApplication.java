package tn.esprit.clubservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubServiceApplication.class, args);
    }

}
