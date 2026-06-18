package com.pipeline.observer.infrastructure.outbound.database.adapter;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import com.pipeline.observer.domain.ports.outbound.MetricRetentionPort;
import com.pipeline.observer.infrastructure.outbound.database.entity.DatabaseMetricsEntity;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemCpuEntity;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemDiskEntity;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemRamEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemCpuRepository;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemDatabaseMetricsRepository;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemDiskRepository;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresMetricAdapter implements MetricPort, MetricRetentionPort {

    private final SystemMemoryRepository memoryRepository;
    private final SystemDiskRepository diskRepository;
    private final SystemCpuRepository cpuRepository;
    private final SystemDatabaseMetricsRepository systemDatabaseMetricsRepository;

    @Override
    public void deleteMetricsOlderThan(LocalDateTime cutoffDate){
        memoryRepository.deleteByTimestampBefore(cutoffDate);
        cpuRepository.deleteByTimestampBefore(cutoffDate);
        diskRepository.deleteByTimestampBefore(cutoffDate);
    }

    @Override
    public void saveFastMetrics(FastMetricsPack fastMetricsPack) {

        LocalDateTime commonTimeStamp = LocalDateTime.now();

        SystemRamEntity memoryEntity = SystemRamEntity.builder()
                .freeMemoryMb(fastMetricsPack.ramRecord().freeMemoryMb())
                .totalMemoryMb(fastMetricsPack.ramRecord().totalMemoryMb())
                .usedMemoryMb(fastMetricsPack.ramRecord().usedMemoryMb())
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

    @Override
    public void saveDatabaseMetrics(DatabaseMetricRecord databaseMetricRecord) {
        LocalDateTime timeStamp = LocalDateTime.now();

        DatabaseMetricsEntity databaseMetricsEntity = DatabaseMetricsEntity.builder()
                .databaseSizeBytes(databaseMetricRecord.dataBaseSizeBytes())
                .activeConnections(databaseMetricRecord.activeConnection())
                .timestamp(timeStamp)
                .build();
        systemDatabaseMetricsRepository.save(databaseMetricsEntity);
    }
}
