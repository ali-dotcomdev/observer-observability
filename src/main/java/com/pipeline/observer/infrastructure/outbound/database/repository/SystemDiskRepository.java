package com.pipeline.observer.infrastructure.outbound.database.repository;

import com.pipeline.observer.infrastructure.outbound.database.entity.SystemDiskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;

public interface SystemDiskRepository extends JpaRepository<SystemDiskEntity, Long> {

    @Transactional
    @Modifying
    void deleteByTimestampBefore(LocalDateTime cutoffDate);
}
