package com.pipeline.observer.infrastructure.outbound.database.repository;

import com.pipeline.observer.infrastructure.outbound.database.entity.DatabaseMetricsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;

public interface SystemDatabaseMetricsRepository extends JpaRepository<DatabaseMetricsEntity, Long> {

    @Transactional
    @Modifying
    void deleteByTimestampBefore(LocalDateTime cutoffDate);
}
