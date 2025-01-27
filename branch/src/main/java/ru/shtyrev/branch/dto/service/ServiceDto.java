package ru.shtyrev.branch.dto.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServiceDto {
    Long id;
    String name;
    String description;
    Integer monthUsages;
}
