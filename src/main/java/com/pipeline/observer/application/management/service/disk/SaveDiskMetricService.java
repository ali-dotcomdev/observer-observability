package com.pipeline.observer.application.management.service.disk;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.disk.SaveDiskMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.disk.SaveDiskMetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveDiskMetricService implements SaveDiskMetricUseCase {

    private final SaveDiskMetricPort saveDiskMetric;

    @Override
    public void saveDiskMetric(DiskRecord diskRecord) {
        saveDiskMetric.saveDiskMetrics(diskRecord);
    }
}
