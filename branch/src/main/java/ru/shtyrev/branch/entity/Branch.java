package ru.shtyrev.branch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "coordinate")
    @JdbcTypeCode(SqlTypes.JSON)
    private Coordinate coordinate;

    @Column(name = "city")
    private String city;

    @Column(name = "avatar_id")
    private String avatarId;

    @Column(name = "average_load")
    private Integer averageLoad;

    @Column(name = "working_hours")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<DayHours> workingHours;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "mark")
    private Double mark;
}