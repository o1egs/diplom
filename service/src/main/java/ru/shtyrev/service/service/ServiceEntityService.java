package ru.shtyrev.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shtyrev.service.dto.CreateServiceDto;
import ru.shtyrev.service.dto.ServiceDto;
import ru.shtyrev.service.dto.ServicePageableResponse;
import ru.shtyrev.service.entity.ServiceEntity;
import ru.shtyrev.service.mapper.ServiceEntityMapper;
import ru.shtyrev.service.repository.ServiceRepository;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceEntityService {
    public final ServiceRepository serviceRepository;
    public final ServiceEntityMapper serviceEntityMapper;

    @Transactional
    public ServicePageableResponse findAll(Integer page, Integer size) {
        long count = serviceRepository.count();

        long pages = count / size;

        if (count % size != 0) {
            pages++;
        }

        List<ServiceDto> serviceDtos = serviceRepository.findAll(PageRequest.of(page, size)).stream()
                .map(serviceEntityMapper::toServiceDto)
                .toList();

        return ServicePageableResponse.builder()
                .pages(pages)
                .services(serviceDtos)
                .build();

    }

    public ServiceDto saveService(CreateServiceDto createServiceDto) {
        log.info("Saving new service");
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .name(createServiceDto.getName())
                .description(createServiceDto.getDescription())
                .monthUsages(0)
                .build();

        return serviceEntityMapper.toServiceDto(serviceRepository.save(serviceEntity));
    }

    public ServiceDto findById(Long serviceId) {
        log.info("Find by id: {}", serviceId);
        return serviceRepository.findById(serviceId)
                .map(serviceEntityMapper::toServiceDto)
                .orElseThrow(RuntimeException::new);
    }

    public List<ServiceDto> findMostPopular(Integer count) {
        log.info("Find {} most popular services", count);
        return serviceRepository.findAll().stream()
                .sorted(comparing(ServiceEntity::getMonthUsages, reverseOrder()))
                .limit(count)
                .map(serviceEntityMapper::toServiceDto)
                .toList();

    }
}
