package ru.shtyrev.branch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtyrev.branch.dto.*;
import ru.shtyrev.branch.dto.prediction.LoadPredictionResponseDto;
import ru.shtyrev.branch.entity.Coordinate;
import ru.shtyrev.branch.service.BranchService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/branch")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<CreateBranchResponseDTO> saveBranch(@RequestBody CreateBranchRequestDTO createBranchRequestDTO) {
        CreateBranchResponseDTO createBranchResponseDTO = branchService.saveBranch(createBranchRequestDTO);
        return ResponseEntity.ok(createBranchResponseDTO);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<Void> updateBranch(@PathVariable Long branchId, @RequestBody UpdateBranchDto updateBranchDto) {
        branchService.updateBranch(branchId, updateBranchDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<BranchDto>> findAll(@PathVariable Integer page, @PathVariable Integer size) {
        List<BranchDto> branchDtos = branchService.findAll(page, size);
        return ResponseEntity.ok(branchDtos);
    }

    @GetMapping
    public ResponseEntity<List<BranchDto>> findAll() {
        List<BranchDto> branchDtos = branchService.findAll();
        return ResponseEntity.ok(branchDtos);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchDto> findById(@PathVariable Long branchId) {
        BranchDto branchDto = branchService.findById(branchId);
        return ResponseEntity.ok(branchDto);
    }

    @PostMapping("/nearest")
    public ResponseEntity<NearestBranchesResponseDto> getNearestBranches(@RequestBody NearestBranchesRequestDto nearestBranchesRequestDto) {
        NearestBranchesResponseDto nearestBranchesResponseDto = branchService.getNearestBranches(nearestBranchesRequestDto);
        return ResponseEntity.ok(nearestBranchesResponseDto);
    }

    @GetMapping("/load/avg/{branchId}")
    public ResponseEntity<Integer> getAverageLoadById(@PathVariable Long branchId) {
        Integer integer = branchService.getAverageLoadById(branchId);
        return ResponseEntity.ok(integer);
    }

    @GetMapping("/load/current/{branchId}")
    public ResponseEntity<Integer> getCurrentLoadById(@PathVariable Long branchId) {
        Integer integer = branchService.getCurrentLoadById(branchId);
        return ResponseEntity.ok(integer);
    }

    @PostMapping("/distance/{branchId}")
    public ResponseEntity<Double> getDistanceById(@PathVariable Long branchId, @RequestBody Coordinate coordinate) {
        Double resultDouble = branchService.getDistanceById(branchId, coordinate);
        return ResponseEntity.ok(resultDouble);
    }


}

