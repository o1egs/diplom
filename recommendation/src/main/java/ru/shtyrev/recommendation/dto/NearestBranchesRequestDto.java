package ru.shtyrev.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
