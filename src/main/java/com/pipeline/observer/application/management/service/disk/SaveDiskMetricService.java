package com.pipeline.observer.application.management.service.disk;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.disk.SaveDiskMetricUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveDiskMetricService implements SaveDiskMetricUseCase {

    private final SaveDiskMetricUseCase saveDiskMetrics;

    @Override
    public void saveDiskMetric(DiskRecord diskRecord) {
        saveDiskMetrics.saveDiskMetric(diskRecord);
    }
}
