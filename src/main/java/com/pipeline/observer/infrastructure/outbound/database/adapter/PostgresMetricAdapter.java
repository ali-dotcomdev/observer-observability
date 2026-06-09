package com.pipeline.observer.infrastructure.outbound.database.adapter;

import com.pipeline.observer.domain.ports.outbound.MetricPorts;
import com.pipeline.observer.infrastructure.outbound.database.SystemMetricEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresMetricAdapter implements MetricPorts {

    private final SystemMetricRepository repository;

    @Override
    public void saveMetrics(int processors, long freeMemoryMb, long totalMemoryMb) {

        SystemMetricEntity entity = SystemMetricEntity.builder()
                .processors(processors)
                .freeMemoryMb(freeMemoryMb)
                .totalMemoryMb(totalMemoryMb)
                .timestamp(LocalDateTime.now())
                .build();
        repository.save(entity);
    }
}
