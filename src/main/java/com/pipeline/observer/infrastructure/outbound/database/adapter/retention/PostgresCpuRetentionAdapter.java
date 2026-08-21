package com.pipeline.observer.infrastructure.outbound.database.adapter.retention;

import com.pipeline.observer.domain.ports.outbound.retention.MetricRetentionPort;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemCpuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresCpuRetentionAdapter implements MetricRetentionPort {

    private final SystemCpuRepository repository;

    @Override
    public void deleteMetricsOlderThan(LocalDateTime cutoffDate) {
        repository.deleteByTimestampBefore(cutoffDate);
    }
}
