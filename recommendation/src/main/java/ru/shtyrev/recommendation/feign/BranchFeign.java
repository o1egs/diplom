package ru.shtyrev.recommendation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shtyrev.recommendation.dto.NearestBranchesRequestDto;
import ru.shtyrev.recommendation.dto.NearestBranchesResponseDto;

@FeignClient(
        name = "branchFeign",
        url = "http://localhost:8080",
        path = "/branch"
)
public interface BranchFeign {

    @PostMapping("/nearest")
    NearestBranchesResponseDto getNearestBranches(@RequestBody NearestBranchesRequestDto nearestBranchesRequestDto);

}
