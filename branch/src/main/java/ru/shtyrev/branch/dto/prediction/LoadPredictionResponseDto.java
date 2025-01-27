package ru.shtyrev.branch.dto.prediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class LoadPredictionResponseDto {
    @JsonProperty("predicted_load")
    List<Load> predictedLoad;
}
