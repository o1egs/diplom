package ru.shtyrev.branch.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Coordinate {
    private Double x;
    private Double y;
}
