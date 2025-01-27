package ru.shtyrev.load.dto.branch;

import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
public class UpdateBranchDto {
    String name;
    Coordinate coordinate;
    String city;
    String avatarId;
    Integer averageLoad;
    List<DayHours> workingHours;
    Long ownerId;
    Integer mark;
}