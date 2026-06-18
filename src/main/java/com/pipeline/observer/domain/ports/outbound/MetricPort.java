package com.pipeline.observer.domain.ports.outbound;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;

public interface MetricPort {

    void saveFastMetrics(FastMetricsPack fastMetricsPack);
    void saveDiskMetrics(DiskRecord diskRecord);
    void saveDatabaseMetrics(DatabaseMetricRecord databaseMetricRecord);
}
