package ru.shtyrev.load.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.shtyrev.load.dto.LoadPredictionRequestDto;
import ru.shtyrev.load.dto.LoadPredictionResponseDto;
import ru.shtyrev.load.dto.branch.BranchDto;
import ru.shtyrev.load.dto.branch.UpdateBranchDto;

@Service
@RequiredArgsConstructor
public class RestService {
    private final RestClient restClient;

    public BranchDto [] findAllBranches() {
        return restClient.get()
                .uri("http://localhost:8080/branch")
                .retrieve()
                .toEntity(BranchDto[].class)
                .getBody();
    }

    public Integer getCurrentLoad(Long branchId) {
        return restClient.get()
                .uri(String.format("http://localhost:8080/branch/load/current/%d", branchId))
                .retrieve()
                .toEntity(Integer.class)
                .getBody();
    }

    public LoadPredictionResponseDto getPrediction(LoadPredictionRequestDto loadPredictionRequestDto) {
        return restClient.post()
                .uri("http://localhost:5000/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .body(loadPredictionRequestDto)
                .retrieve()
                .toEntity(LoadPredictionResponseDto.class)
                .getBody();
    }

    public void updateBranchById(Long branchId, UpdateBranchDto updateBranchDto) {
        restClient.put()
                .uri(String.format("http://localhost:8080/branch/%d", branchId))
                .body(updateBranchDto)
                .retrieve()
                .toBodilessEntity();
    }
}
