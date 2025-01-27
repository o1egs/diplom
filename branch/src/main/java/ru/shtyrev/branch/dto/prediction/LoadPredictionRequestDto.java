package ru.shtyrev.branch.dto.prediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class LoadPredictionRequestDto {
    List<Data> data;
    @JsonProperty("predict_date")
    LocalDate predictDate;
}
