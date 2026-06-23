package com.pipeline.observer.domain.ports.inbound.usecase.database;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;

public interface DatabaseMetricUseCase {

    DatabaseMetricRecord calculateDatabaseMetrics();
}
