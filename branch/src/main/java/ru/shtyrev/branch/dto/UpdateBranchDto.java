package ru.shtyrev.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import ru.shtyrev.branch.entity.Coordinate;
import ru.shtyrev.branch.entity.DayHours;

import java.util.List;

/**
 * DTO for {@link ru.shtyrev.branch.entity.Branch}
 */
@Value
public class UpdateBranchDto {
    String name;
    Coordinate coordinate;
    String city;
    String avatarId;
    Integer averageLoad;
    List<DayHours> workingHours;
    Long ownerId;
    Double mark;
}