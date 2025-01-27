package ru.shtyrev.service.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ServicePageableResponse {
    List<ServiceDto> services;
    Long pages;
}
