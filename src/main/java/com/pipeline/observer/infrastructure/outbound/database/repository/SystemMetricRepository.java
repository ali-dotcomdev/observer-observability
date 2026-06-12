package com.pipeline.observer.infrastructure.outbound.database.repository;

import com.pipeline.observer.infrastructure.outbound.database.SystemMetricEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;

public interface SystemMetricRepository extends JpaRepository<SystemMetricEntity, Long> {

    @Transactional
    @Modifying
    void deleteByTimestampBefore(LocalDateTime cutoffDate);
}
