package com.pipeline.observer.infrastructure.outbound.database.adapter;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;
import com.pipeline.observer.domain.model.SystemMetricSnapshot;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import com.pipeline.observer.domain.ports.outbound.RetentionLogPort;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemCpuEntity;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemDiskEntity;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemMemoryEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemCpuRepository;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemDiskRepository;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresMetricAdapter implements MetricPort, RetentionLogPort {

    private final SystemMemoryRepository memoryRepository;
    private final SystemDiskRepository diskRepository;
    private final SystemCpuRepository cpuRepository;

    @Override
    public void deleteMetricsOlderThan(LocalDateTime cutoffDate){
        memoryRepository.deleteByTimestampBefore(cutoffDate);
        cpuRepository.deleteByTimestampBefore(cutoffDate);
        diskRepository.deleteByTimestampBefore(cutoffDate);
    }

    @Override
    public void saveFastMetrics(FastMetricsPack fastMetricsPack) {

        LocalDateTime commonTimeStamp = LocalDateTime.now();

        SystemMemoryEntity memoryEntity = SystemMemoryEntity.builder()
                .freeMemoryMb(fastMetricsPack.memoryRecord().freeMemoryMb())
                .totalMemoryMb(fastMetricsPack.memoryRecord().totalMemoryMb())
                .usedMemoryMb(fastMetricsPack.memoryRecord().usedMemoryMb())
                .timestamp(commonTimeStamp)
                .build();
        memoryRepository.save(memoryEntity);

        SystemCpuEntity cpuEntity = SystemCpuEntity.builder()
                .cpuLoad(fastMetricsPack.cpuRecord().cpuLoad())
                .availableProcessors(fastMetricsPack.cpuRecord().availableProcessors())
                .timestamp(commonTimeStamp)
                .build();
        cpuRepository.save(cpuEntity);
    }

    @Override
    public void saveDiskMetrics(DiskRecord diskRecord) {

        LocalDateTime timeStamp = LocalDateTime.now();

        SystemDiskEntity diskEntity = SystemDiskEntity.builder()
                .freeSpaceGb(diskRecord.freeSpaceGb())
                .totalSpaceGb(diskRecord.totalSpaceGb())
                .usedSpaceGb(diskRecord.usedSpaceGb())
                .timestamp(timeStamp)
                .build();
        diskRepository.save(diskEntity);
    }
}
