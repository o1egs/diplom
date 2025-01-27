package ru.shtyrev.load.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtyrev.load.dto.LoadPredictionResponseDto;
import ru.shtyrev.load.dto.PredictionResponse;
import ru.shtyrev.load.service.LoadService;

@RestController
@CrossOrigin("*")
@RequestMapping("/load")
@RequiredArgsConstructor
public class LoadController {
    private final LoadService loadService;

    @PostMapping("/predict")
    public ResponseEntity<LoadPredictionResponseDto> getPredictionById(@RequestBody PredictionResponse predictionResponse) {
        LoadPredictionResponseDto prediction = loadService.getPrediction(predictionResponse.getBranchId(), predictionResponse.getDate());

        return ResponseEntity.ok(prediction);
    }
}
