package ru.shtyrev.recommendation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shtyrev.recommendation.dto.LoadPredictionResponseDto;
import ru.shtyrev.recommendation.dto.PredictionRequest;

@FeignClient(
        name = "loadFeign",
        url = "http://localhost:8081",
        path = "/load"
)
public interface PredictFeign {
    @PostMapping("/predict")
    LoadPredictionResponseDto getPredictionById(@RequestBody PredictionRequest predictionResponse);
}
