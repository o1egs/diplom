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
public class CreateBranchResponseDTO {
    private Long id;
    private String name;
    private Coordinate coordinate;
    private String avatarId;
    private List<DayHours> workingHours;
    private Long ownerId;
}