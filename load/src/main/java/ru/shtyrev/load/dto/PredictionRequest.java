package ru.shtyrev.load.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PredictionRequest {
    Long branchId;
    LocalDate date;
}
