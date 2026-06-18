package com.pipeline.observer.application.management.service;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.DatabaseMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.DatabaseMonitorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseMonitorService implements DatabaseMetricUseCase {

    private final DatabaseMonitorPort databaseMonitorPort;

    public DatabaseMetricRecord calculateDatabaseMetrics(){

        DatabaseMetricRecord databaseMetricRecord = new DatabaseMetricRecord(databaseMonitorPort.fetchActiveConnections(), databaseMonitorPort.fetchDatabaseSizeBytes());
        return databaseMetricRecord;
    }
}
