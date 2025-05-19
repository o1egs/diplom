package ru.shtyrev.recommendation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shtyrev.recommendation.dto.BranchSmallDto;
import ru.shtyrev.recommendation.dto.Coordinate;
import ru.shtyrev.recommendation.service.RecommendationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public List<BranchSmallDto> getRecommendation(@RequestParam Long serviceId,
                                                  @RequestParam Double x,
                                                  @RequestParam Double y,
                                                  @RequestParam LocalDate date,
                                                  @RequestParam LocalTime time) {

        List<BranchSmallDto> recommendation = recommendationService.getRecommendation(serviceId, new Coordinate(x, y), date, time);
        return recommendation;
    }

}
