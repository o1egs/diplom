package ru.shtyrev.branch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.shtyrev.branch.dto.BranchDto;
import ru.shtyrev.branch.dto.CreateBranchRequestDTO;
import ru.shtyrev.branch.dto.CreateBranchResponseDTO;
import ru.shtyrev.branch.entity.Branch;
import ru.shtyrev.branch.dto.BranchSmallDto;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchMapper {
    Branch toEntity(CreateBranchRequestDTO createBranchRequestDTO);

    CreateBranchResponseDTO toCreateBranchResponseDTO(Branch branch);

    BranchSmallDto toSmallBranchDto(Branch branch);

    BranchDto toBranchDto(Branch branch);
}