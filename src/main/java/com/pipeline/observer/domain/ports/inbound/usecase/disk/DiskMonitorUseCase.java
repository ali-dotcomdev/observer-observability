package com.pipeline.observer.domain.ports.inbound.usecase.disk;

import com.pipeline.observer.domain.model.DiskRecord;

public interface DiskMonitorUseCase {

    DiskRecord measureDiskMetric();
}
