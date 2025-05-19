package ru.shtyrev.branch.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shtyrev.branch.dto.*;
import ru.shtyrev.branch.dto.provided.ProvidedServiceDto;
import ru.shtyrev.branch.entity.Branch;
import ru.shtyrev.branch.entity.Coordinate;
import ru.shtyrev.branch.mapper.BranchMapper;
import ru.shtyrev.branch.repository.BranchRepository;
import ru.shtyrev.branch.repository.ProvidedServiceRepository;

import java.util.List;
import java.util.Random;

import static java.lang.Math.*;
import static java.util.Comparator.*;
import static lombok.AccessLevel.*;


@Service
@FieldDefaults(
        level = PRIVATE,
        makeFinal = true
)
@RequiredArgsConstructor
public class BranchService {
    ProvidedServiceService providedServiceService;
    BranchRepository branchRepository;
    BranchMapper branchMapper;

    public CreateBranchResponseDTO saveBranch(CreateBranchRequestDTO createBranchRequestDTO) {
        Branch branch = branchMapper.toEntity(createBranchRequestDTO);

        return branchMapper.toCreateBranchResponseDTO(branchRepository.save(branch));
    }

    @Transactional
    public void updateBranch(Long branchId, UpdateBranchDto updateBranchDto) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(RuntimeException::new);

        if (updateBranchDto.getName() != null) {
            branch.setName(updateBranchDto.getName());
        }
        if (updateBranchDto.getCoordinate() != null) {
            if (updateBranchDto.getCoordinate().getX() != null && updateBranchDto.getCoordinate().getY() != null) {
                Coordinate coordinate = Coordinate.builder()
                        .x(updateBranchDto.getCoordinate().getX())
                        .y(updateBranchDto.getCoordinate().getY())
                        .build();
                branch.setCoordinate(coordinate);
            }
        }
        if (updateBranchDto.getCity() != null) {
            branch.setCity(updateBranchDto.getCity());
        }
        if (updateBranchDto.getAvatarId() != null) {
            branch.setAvatarId(updateBranchDto.getAvatarId());
        }
        if (updateBranchDto.getAverageLoad() != null) {
            branch.setAverageLoad(updateBranchDto.getAverageLoad());
        }
        if (updateBranchDto.getWorkingHours() != null) {
            branch.setWorkingHours(updateBranchDto.getWorkingHours());
        }
        if (updateBranchDto.getOwnerId() != null) {
            branch.setOwnerId(updateBranchDto.getOwnerId());
        }
        if (updateBranchDto.getMark() != null) {
            branch.setMark(updateBranchDto.getMark());
        }

        branchRepository.save(branch);
    }

    public List<BranchDto> findAll(Integer page, Integer size) {
        return branchRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(branchMapper::toBranchDto)
                .toList();
    }

    public List<BranchDto> findAll() {
        return branchRepository.findAll()
                .stream()
                .map(branchMapper::toBranchDto)
                .toList();
    }

    public BranchDto findById(Long branchId) {
        return branchRepository.findById(branchId)
                .map(branchMapper::toBranchDto)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public NearestBranchesResponseDto getNearestBranches(NearestBranchesRequestDto nearestBranchesRequestDto) {
        List<Branch> branches;

        String city = nearestBranchesRequestDto.getCity();

        if (city != null && !city.isEmpty()) {
            branches = branchRepository.findBranchesByCity(city);
        } else {
            branches = branchRepository.findAll();
        }

        Coordinate coordinate = nearestBranchesRequestDto.getCoordinate();

        List<BranchSmallDto> nearestBranches = branches.stream()
                .sorted(comparing(branch -> getDistance(branch, coordinate)))
                .limit(nearestBranchesRequestDto.getSize())
                .map(branch -> {
                    List<Long> ids = providedServiceService.findAllProvidedServicesByBranchId(branch.getId()).stream()
                            .map(ps -> ps.getServiceDto().getId())
                            .toList();
                    Double distance = getDistance(branch, coordinate);
                    BranchSmallDto smallBranchDto = branchMapper.toSmallBranchDto(branch);
                    smallBranchDto.setDistance(distance);
                    smallBranchDto.setCoordinate(branch.getCoordinate());
                    smallBranchDto.setProvidedServiceId(ids);
                    return smallBranchDto;
                })
                .toList();

        int size = nearestBranchesRequestDto.getSize() > nearestBranches.size()
                ? nearestBranchesRequestDto.getSize()
                : nearestBranches.size();

        return NearestBranchesResponseDto.builder()
                .nearestBranches(nearestBranches)
                .size(size)
                .build();
    }

    public Double getDistance(Branch branch, Coordinate coordinate) {
        Coordinate branchCoordinate = branch.getCoordinate();

        if (branchCoordinate != null) {
//            double distance = sqrt(pow(branchCoordinate.getX() - coordinate.getX(), 2) + pow(branchCoordinate.getY() - coordinate.getY(), 2));
//            return distance;
            Integer r = 6371000;
            Double p = PI / 180;

            Double a = 0.5 - cos((branchCoordinate.getX()-coordinate.getX())*p)/2 + cos(branchCoordinate.getX()*p) * cos(coordinate.getX()*p) * (1-cos((coordinate.getY()-branchCoordinate.getY())*p))/2;
            return 2 * r * asin(sqrt(a));
        }
        return new Random().nextDouble(500);
    }

    public Double getDistanceById(Long branchId, Coordinate coordinate) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow();

        return getDistance(branch, coordinate);
    }

    public Integer getCurrentLoadById(Long branchId) {
        return new Random().nextInt(3);
    }

    public Integer getAverageLoadById(Long branchId) {
        return new Random().nextInt(10);
    }
}
