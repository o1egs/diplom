package ru.shtyrev.recommendation.dto;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link ru.shtyrev.service.entity.ServiceEntity}
 */
@Value
@Builder
public class ServiceDto {
    Long id;
    String name;
    String description;
    Integer monthUsages;
}