package com.pipeline.observer.application.management.service.database;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.database.SaveDatabaseMetricsUseCase;
import com.pipeline.observer.domain.ports.outbound.database.SaveDatabaseMetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveDatabaseMetricsService implements SaveDatabaseMetricsUseCase {

    private final SaveDatabaseMetricPort saveDatabase;

    @Override
    public void saveDatabaseMetrics(DatabaseMetricRecord databaseMetrics){
        saveDatabase.saveDatabaseMetrics(databaseMetrics);
    }
}
