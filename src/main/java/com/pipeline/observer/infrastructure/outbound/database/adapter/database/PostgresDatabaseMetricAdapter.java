package com.pipeline.observer.infrastructure.outbound.database.adapter.database;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.ports.outbound.database.SaveDatabaseMetricPort;
import com.pipeline.observer.infrastructure.outbound.database.entity.DatabaseMetricsEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemDatabaseMetricsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostgresDatabaseMetricAdapter implements SaveDatabaseMetricPort {

    private final SystemDatabaseMetricsRepository repository;

    @Override
    public void saveDatabaseMetrics(DatabaseMetricRecord databaseRecord) {

        try{
            DatabaseMetricsEntity entity = DatabaseMetricsEntity.builder()
                    .databaseSizeBytes(databaseRecord.databaseSizeBytes())
                    .activeConnections(databaseRecord.activeConnections())
                    .timestamp(LocalDateTime.now())
                    .build();
            repository.save(entity);

        } catch (Exception e){
            log.error("db çökmüş olabilir!!: {}", e.getMessage());
        }

    }
}
