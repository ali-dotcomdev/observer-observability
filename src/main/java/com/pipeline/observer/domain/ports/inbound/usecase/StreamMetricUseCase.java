package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;

public interface StreamMetricUseCase {

    void streamMetrics(FastMetricsPack fastMetricsPack);
    void streamDiskMetrics(DiskRecord diskRecord);
}
