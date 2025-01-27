package ru.shtyrev.branch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtyrev.branch.dto.provided.AddProvidedServiceDto;
import ru.shtyrev.branch.dto.provided.ProvidedServiceDto;
import ru.shtyrev.branch.service.ProvidedServiceService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/provided")
@RequiredArgsConstructor
public class ProvidedServiceController {

    private final ProvidedServiceService providedServiceService;

    @PostMapping
    public ResponseEntity<ProvidedServiceDto> addProvidedService(@RequestBody AddProvidedServiceDto addProvidedServiceDto) {
        ProvidedServiceDto providedServiceDto = providedServiceService.addProvidedService(addProvidedServiceDto);
        return ResponseEntity.ok(providedServiceDto);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<List<ProvidedServiceDto>> findAllProvidedServicesByBranchId(@PathVariable Long branchId) {
        List<ProvidedServiceDto> providedServiceDtos = providedServiceService.findAllProvidedServicesByBranchId(branchId);
        return ResponseEntity.ok(providedServiceDtos);
    }


    @GetMapping("/{branchId}/{count}")
    public ResponseEntity<List<ProvidedServiceDto>> findAllProvidedServicesByBranchId(@PathVariable Long branchId, @PathVariable Integer count) {
        List<ProvidedServiceDto> providedServiceDtos = providedServiceService.findAllProvidedServicesByBranchId(branchId, count);
        return ResponseEntity.ok(providedServiceDtos);
    }
}
