package ru.shtyrev.load.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    @JdbcTypeCode(SqlTypes.TIME)
    private LocalTime time;

    @Column(name = "load", nullable = false)
    private Integer load;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;
}
