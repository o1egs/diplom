package ru.shtyrev.branch.dto;

import lombok.*;
import ru.shtyrev.branch.entity.Coordinate;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class NearestBranchesRequestDto {
    private String city;
    private Coordinate coordinate;
    private Integer size;
}
