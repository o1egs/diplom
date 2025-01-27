package ru.shtyrev.branch.dto;

import lombok.*;
import ru.shtyrev.branch.entity.Coordinate;
import ru.shtyrev.branch.entity.DayHours;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class CreateBranchRequestDTO {
    @NonNull
    private String name;
    @NonNull
    private Coordinate coordinate;
    private List<DayHours> workingHours;
    private Long ownerId;
}