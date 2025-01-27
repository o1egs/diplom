package ru.shtyrev.load.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Data {
    private LocalDate date;
    private List<LoadTime> load;
}
