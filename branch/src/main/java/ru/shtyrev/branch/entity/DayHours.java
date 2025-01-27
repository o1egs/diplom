package ru.shtyrev.branch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DayHours {
    private DayOfWeek day;
    private String start;
    private String end;
}
