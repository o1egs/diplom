package ru.shtyrev.load.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class LoadPredictionResponseDto {
    @JsonProperty("predicted_load")
    List<LoadTime> predictedLoad;
}
