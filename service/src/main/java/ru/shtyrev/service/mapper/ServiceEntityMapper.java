package ru.shtyrev.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.shtyrev.service.dto.ServiceDto;
import ru.shtyrev.service.entity.ServiceEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceEntityMapper {
    ServiceEntity toEntity(ServiceDto serviceDto);

    ServiceDto toServiceDto(ServiceEntity serviceEntity);
}