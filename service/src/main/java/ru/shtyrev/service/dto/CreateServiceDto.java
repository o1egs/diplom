package ru.shtyrev.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * DTO for {@link ru.shtyrev.service.entity.ServiceEntity}
 */
@Value
public class CreateServiceDto {
    @NotNull
    @NotEmpty
    @NotBlank
    String name;
    String description;
}