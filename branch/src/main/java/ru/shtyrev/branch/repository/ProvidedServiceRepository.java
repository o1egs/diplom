package ru.shtyrev.branch.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.shtyrev.branch.entity.ProvidedService;

import java.util.List;

public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, Long>,
        JpaSpecificationExecutor<ProvidedService> {
List<ProvidedService> findByBranch_Id(Long id, Pageable pageable);
}