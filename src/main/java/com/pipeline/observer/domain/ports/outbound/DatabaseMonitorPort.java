package com.pipeline.observer.domain.ports.outbound;

public interface DatabaseMonitorPort {

    int fetchActiveConnections();
    long fetchDatabaseSizeBytes();
}
