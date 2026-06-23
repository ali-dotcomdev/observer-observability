package com.pipeline.observer.application.management.service.database;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.database.SaveDatabaseMetricsUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveDatabaseMetricsService implements SaveDatabaseMetricsUseCase {

    private final MetricPort metricPort;

    @Override
    public void saveDatabaseMetrics(DatabaseMetricRecord databaseMetrics){
        metricPort.saveDatabaseMetrics(databaseMetrics);
    }
}
