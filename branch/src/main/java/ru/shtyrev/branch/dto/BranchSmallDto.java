package ru.shtyrev.branch.dto;

import lombok.*;
import ru.shtyrev.branch.entity.Coordinate;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class BranchSmallDto {
    private String id;
    private String name;
    private String avatarId;
    private Double mark;
    private Double distance;
    private Coordinate coordinate;
    private List<Long> providedServiceId;
}