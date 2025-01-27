package ru.shtyrev.load.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class LoadTime {
    private String time;
    private int load;
}
