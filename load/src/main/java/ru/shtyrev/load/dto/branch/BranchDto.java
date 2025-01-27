package ru.shtyrev.load.dto.branch;

import lombok.Value;

import java.util.List;

@Value
public class BranchDto {
    Long id;
    String name;
    Coordinate coordinate;
    String city;
    String avatarId;
    Integer averageLoad;
    List<DayHours> workingHours;
    Long ownerId;
    Integer mark;
}
