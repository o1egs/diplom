package ru.shtyrev.branch.dto.provided;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.shtyrev.branch.dto.service.ServiceDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvidedServiceDto {
    Long id;
    Long branchId;
    @JsonProperty("service")
    ServiceDto serviceDto;
}