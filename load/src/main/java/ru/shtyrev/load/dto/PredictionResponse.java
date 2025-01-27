package ru.shtyrev.load.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PredictionResponse {
    Long branchId;
    LocalDate date;
}
