package com.pipeline.observer.domain.ports.outbound.disk;

import com.pipeline.observer.domain.model.DiskRecord;

public interface SaveDiskMetricPort {
    void saveDiskMetrics(DiskRecord diskRecord);
}
