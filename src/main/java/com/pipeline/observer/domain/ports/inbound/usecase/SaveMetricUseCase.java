package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;

public interface SaveMetricUseCase {
    void saveFastMetrics(FastMetricsPack fastMetricsPack);
    void saveDiskMetric(DiskRecord diskRecord);
}
