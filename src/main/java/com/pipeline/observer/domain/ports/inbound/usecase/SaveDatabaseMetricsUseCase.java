package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;

public interface SaveDatabaseMetricsUseCase {
    void saveDatabaseMetrics(DatabaseMetricRecord databaseMetrics);
}
