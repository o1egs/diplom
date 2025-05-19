package ru.shtyrev.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class BranchSmallDto {
    private Long id;
    private String name;
    private String avatarId;
    private Double mark;
    private Double distance;
    private Coordinate coordinate;
    private List<Long> providedServiceId;
    private Integer load;
}