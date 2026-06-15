package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.domain.model.DiskRecord;

public interface DiskMetricUseCase {

    DiskRecord calculateDiskRecord();
}
