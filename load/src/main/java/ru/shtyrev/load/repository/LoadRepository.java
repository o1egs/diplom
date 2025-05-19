package ru.shtyrev.load.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shtyrev.load.entity.Load;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoadRepository extends JpaRepository<Load, Long> {
    List<Load> findByBranchIdAndDateGreaterThan(Long branchId, LocalDate date);

    List<Load> findByBranchIdAndDateBetween(Long id, LocalDate dateStart, LocalDate dateEnd);

    long countByIdAndDateBetween(Long id, LocalDate dateStart, LocalDate dateEnd);
}