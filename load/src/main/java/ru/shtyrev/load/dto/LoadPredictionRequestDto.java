package ru.shtyrev.load.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class LoadPredictionRequestDto {
    List<Data> data;
    @JsonProperty("predict_date")
    LocalDate predictDate;
}
