package ru.shtyrev.branch.dto.prediction;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Load {
    private String time;
    private int load;
}
