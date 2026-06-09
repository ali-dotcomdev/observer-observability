package com.pipeline.observer.infrastructure.outbound.database.repository;

import com.pipeline.observer.infrastructure.outbound.database.SystemMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemMetricRepository extends JpaRepository<SystemMetricEntity, Long> {

}
