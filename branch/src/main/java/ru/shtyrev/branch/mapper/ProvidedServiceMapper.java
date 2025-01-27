package ru.shtyrev.branch.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shtyrev.branch.dto.provided.ProvidedServiceDto;
import ru.shtyrev.branch.dto.service.ServiceDto;
import ru.shtyrev.branch.entity.ProvidedService;
import ru.shtyrev.branch.service.RestService;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ProvidedServiceMapper {
    @Autowired
    protected RestService restService;

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(expression = "java(restService.findServiceById(providedService.getServiceId()))", target = "serviceDto")
    public abstract ProvidedServiceDto toDto(ProvidedService providedService);
}
