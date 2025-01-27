package ru.shtyrev.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtyrev.service.dto.CreateServiceDto;
import ru.shtyrev.service.dto.ServiceDto;
import ru.shtyrev.service.dto.ServicePageableResponse;
import ru.shtyrev.service.service.ServiceEntityService;

import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceEntityService serviceEntityService;

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceDto> findById(@PathVariable Long serviceId) {
        ServiceDto serviceDto = serviceEntityService.findById(serviceId);
        return ResponseEntity.ok(serviceDto);
    }

    @GetMapping("/popular/{count}")
    public ResponseEntity<List<ServiceDto>> findMostPopular(@PathVariable Integer count) {
        List<ServiceDto> serviceDtos = serviceEntityService.findMostPopular(count);
        return ResponseEntity.ok(serviceDtos);
    }

    @PostMapping
    public ResponseEntity<ServiceDto> saveService(@RequestBody CreateServiceDto createServiceDto) {
        ServiceDto serviceDto = serviceEntityService.saveService(createServiceDto);
        return ResponseEntity.ok(serviceDto);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<ServicePageableResponse> findAll(@PathVariable Integer page, @PathVariable Integer size) {
        ServicePageableResponse servicePageableResponse = serviceEntityService.findAll(page, size);
        return ResponseEntity.ok(servicePageableResponse);
    }
}

