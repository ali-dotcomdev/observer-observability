package com.pipeline.observer.domain.ports.outbound.database;

public interface DatabaseMonitorPort {

    int fetchActiveConnections();
    long fetchDatabaseSizeBytes();
}
