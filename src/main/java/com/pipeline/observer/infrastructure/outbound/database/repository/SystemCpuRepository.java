package com.pipeline.observer.infrastructure.outbound.database.repository;

import com.pipeline.observer.infrastructure.outbound.database.entity.SystemCpuEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;

public interface SystemCpuRepository extends JpaRepository<SystemCpuEntity, Long> {

    @Transactional
    @Modifying
    void deleteByTimestampBefore(LocalDateTime cutoffDate);
}
