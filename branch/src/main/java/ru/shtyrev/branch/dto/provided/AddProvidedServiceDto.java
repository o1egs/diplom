package ru.shtyrev.branch.dto.provided;

import lombok.Value;

@Value
public class AddProvidedServiceDto {
    Long branchId;
    Long serviceId;
}
