package ru.shtyrev.load.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtyrev.load.dto.DayLoadDto;
import ru.shtyrev.load.dto.LoadPredictionResponseDto;
import ru.shtyrev.load.dto.PredictionRequest;
import ru.shtyrev.load.entity.Load;
import ru.shtyrev.load.service.LoadService;

import java.time.Month;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/load")
@RequiredArgsConstructor
public class LoadController {
    private final LoadService loadService;

    @PostMapping("/predict")
    public ResponseEntity<LoadPredictionResponseDto> getPredictionById(@RequestBody PredictionRequest predictionResponse) {
        LoadPredictionResponseDto prediction = loadService.getPrediction(predictionResponse.getBranchId(), predictionResponse.getDate());

        return ResponseEntity.ok(prediction);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<List<Load>> getMonthLoadList(@PathVariable Long branchId) {
        List<Load> loads = loadService.getLastMonthLoadList(branchId);
        return ResponseEntity.ok(loads);
    }

    @GetMapping("/month")
    public ResponseEntity<List<Load>> getMonthLoadList(@RequestParam Long branchId, @RequestParam Month month, @RequestParam Integer year) {
        List<Load> loads = loadService.getMonthLoadListAvg(branchId, month, year);
        return ResponseEntity.ok(loads);
    }

    @GetMapping("/date/max")
    public ResponseEntity<DayLoadDto> findDayWithMaxLoad(@RequestParam Long branchId, @RequestParam Month month, @RequestParam Integer year) {
        DayLoadDto dayWithMaxLoad = loadService.findDayWithMaxLoad(branchId, month, year);
        return ResponseEntity.ok(dayWithMaxLoad);
    }

    @GetMapping("/date/min")
    public ResponseEntity<DayLoadDto> findDayWithMinLoad(@RequestParam Long branchId, @RequestParam Month month, @RequestParam Integer year) {
        DayLoadDto dayWithMaxLoad = loadService.findDayWithMinLoad(branchId, month, year);
        return ResponseEntity.ok(dayWithMaxLoad);
    }

    @GetMapping("/best/time")
    public ResponseEntity<String> getBestTimeByMonth(@RequestParam Long branchId, @RequestParam Month month, @RequestParam Integer year) {
        String string = loadService.getBestTimeByMonth(branchId, month, year);
        return ResponseEntity.ok(string);
    }

    @GetMapping("/worst/time")
    public ResponseEntity<String> getWorstTimeByMonth(@RequestParam Long branchId, @RequestParam Month month, @RequestParam Integer year) {
        String string = loadService.getWorstTimeByMonth(branchId, month, year);
        return ResponseEntity.ok(string);
    }
}
