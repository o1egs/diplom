package ru.shtyrev.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.shtyrev.service.entity.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long>,
        JpaSpecificationExecutor<ServiceEntity> {

}