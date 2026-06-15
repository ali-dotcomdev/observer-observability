package com.pipeline.observer.domain.ports.inbound;

import com.pipeline.observer.domain.model.DiskRecord;

public interface DiskMetricUseCase {

    DiskRecord calculateDiskRecord();
}
