package ru.shtyrev.branch.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class NearestBranchesResponseDto {
    private Integer size;

    private List<BranchSmallDto> nearestBranches;
}
