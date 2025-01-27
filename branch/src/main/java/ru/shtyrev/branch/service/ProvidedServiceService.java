package ru.shtyrev.branch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shtyrev.branch.dto.provided.AddProvidedServiceDto;
import ru.shtyrev.branch.dto.provided.ProvidedServiceDto;
import ru.shtyrev.branch.dto.service.ServiceDto;
import ru.shtyrev.branch.entity.Branch;
import ru.shtyrev.branch.entity.ProvidedService;
import ru.shtyrev.branch.mapper.ProvidedServiceMapper;
import ru.shtyrev.branch.repository.BranchRepository;
import ru.shtyrev.branch.repository.ProvidedServiceRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProvidedServiceService {
    private final BranchRepository branchRepository;
    private final ProvidedServiceRepository providedServiceRepository;

    private final ProvidedServiceMapper providedServiceMapper;

    private final RestService restService;

    public ProvidedServiceDto addProvidedService(AddProvidedServiceDto addProvidedServiceDto) {
        Branch branch = branchRepository.findById(addProvidedServiceDto.getBranchId())
                .orElseThrow();

        ServiceDto serviceDto = restService.findServiceById(addProvidedServiceDto.getServiceId());

        assert serviceDto != null;

        ProvidedService providedService = ProvidedService.builder()
                .serviceId(serviceDto.getId())
                .branch(branch)
                .build();

        ProvidedService saved = providedServiceRepository.save(providedService);

        return ProvidedServiceDto.builder()
                .id(saved.getId())
                .branchId(branch.getId())
                .serviceDto(serviceDto)
                .build();
    }

    public List<ProvidedServiceDto> findAllProvidedServicesByBranchId(Long branchId, Integer count) {
        return providedServiceRepository.findByBranch_Id(branchId, Pageable.ofSize(count)).stream()
                .map(providedServiceMapper::toDto)
                .toList();
    }

    public List<ProvidedServiceDto> findAllProvidedServicesByBranchId(Long branchId) {
        return providedServiceRepository.findByBranch_Id(branchId, Pageable.ofSize(100)).stream()
                .map(providedServiceMapper::toDto)
                .toList();
    }
}
