package ru.shtyrev.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shtyrev.recommendation.dto.BranchSmallDto;
import ru.shtyrev.recommendation.dto.Coordinate;
import ru.shtyrev.recommendation.dto.LoadPredictionResponseDto;
import ru.shtyrev.recommendation.dto.LoadTime;
import ru.shtyrev.recommendation.dto.NearestBranchesRequestDto;
import ru.shtyrev.recommendation.dto.NearestBranchesResponseDto;
import ru.shtyrev.recommendation.dto.PredictionRequest;
import ru.shtyrev.recommendation.dto.ServiceDto;
import ru.shtyrev.recommendation.feign.BranchFeign;
import ru.shtyrev.recommendation.feign.PredictFeign;
import ru.shtyrev.recommendation.feign.ServiceFeign;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final BranchFeign branchFeign;
    private final ServiceFeign serviceFeign;
    private final PredictFeign predictFeign;

    public List<BranchSmallDto> getRecommendation(Long serviceId, Coordinate coordinate, LocalDate date, LocalTime time) {

        ServiceDto serviceDto = serviceFeign.findById(serviceId);

        if (serviceDto == null) {
            return null;
        }

        NearestBranchesResponseDto nearestBranchesResponseDto = branchFeign.getNearestBranches(new NearestBranchesRequestDto(null, coordinate, 10));

        if (nearestBranchesResponseDto == null) {
            return null;
        }

        List<BranchSmallDto> nearestBranches = nearestBranchesResponseDto.getNearestBranches().stream()
                .filter(branch -> branch.getProvidedServiceId().contains(serviceId))
                .toList();

        if (nearestBranches.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        Map<Long, LoadPredictionResponseDto> predictions = new HashMap<>();

        for (BranchSmallDto nearestBranch : nearestBranches) {

            Long id = nearestBranch.getId();

            PredictionRequest predictionRequest = PredictionRequest.builder()
                    .date(date)
                    .branchId(id).build();
            try {
                LoadPredictionResponseDto prediction = predictFeign.getPredictionById(predictionRequest);

                predictions.put(id, prediction);
            } catch (Exception ex) {
                System.out.println("Exception while fetching prediction");
            }
        }

        Map<Long, LoadTime> longLoadTimeMap = new HashMap<>();

        for (Map.Entry<Long, LoadPredictionResponseDto> prediction : predictions.entrySet()) {
            LoadTime loadTime = prediction.getValue().getPredictedLoad().stream()
                    .filter(l -> l.getTime().contains(String.valueOf(time.getHour())))
                    .findFirst()
                    .orElse(null);

            Optional.ofNullable(loadTime)
                    .ifPresent(l -> longLoadTimeMap.put(prediction.getKey(), l));
        }

        return nearestBranches.stream()
                .filter(branch -> longLoadTimeMap.get(branch.getId()) != null)
                .sorted(
                        Comparator.comparing(BranchSmallDto::getDistance)
                                .thenComparing(branchSmallDto -> longLoadTimeMap.get(branchSmallDto.getId()).getLoad())
                )
                .peek(branch -> branch.setLoad(longLoadTimeMap.get(branch.getId()).getLoad()))
                .toList();
    }
}
