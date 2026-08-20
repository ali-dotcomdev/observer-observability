package com.pipeline.observer.domain.ports.outbound.database;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;

public interface SaveDatabaseMetricPort {

    void saveDatabaseMetrics(DatabaseMetricRecord databaseRecord);
}
