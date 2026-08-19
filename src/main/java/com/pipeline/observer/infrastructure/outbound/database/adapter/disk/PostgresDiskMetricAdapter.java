package com.pipeline.observer.infrastructure.outbound.database.adapter.disk;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.ports.outbound.disk.SaveDiskMetricPort;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemDiskEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemDiskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresDiskMetricAdapter implements SaveDiskMetricPort {

    private final SystemDiskRepository systemDiskRepository;

    @Override
    public void saveDiskMetrics(DiskRecord diskRecord) {

        SystemDiskEntity systemDiskEntity = SystemDiskEntity.builder()
                .freeSpaceGb(diskRecord.freeSpaceGb())
                .totalSpaceGb(diskRecord.totalSpaceGb())
                .usedSpaceGb(diskRecord.usedSpaceGb())
                .timestamp(LocalDateTime.now())
                .build();
        systemDiskRepository.save(systemDiskEntity);
    }
}
