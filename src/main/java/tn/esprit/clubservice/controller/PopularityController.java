package tn.esprit.clubservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.clubservice.service.PopularityJobService;

@RestController
@RequestMapping("/api/clubs/popularity")
public class PopularityController {

    private final PopularityJobService popularityJobService;

    public PopularityController(PopularityJobService popularityJobService) {
        this.popularityJobService = popularityJobService;
    }

    @GetMapping("/top")
    public String getTopClub() {
        return popularityJobService.getMostPopularClub();
    }
}
