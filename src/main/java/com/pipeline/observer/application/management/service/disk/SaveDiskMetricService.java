package com.pipeline.observer.application.management.service.disk;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.disk.SaveDiskMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveDiskMetricService implements SaveDiskMetricUseCase {

    private final MetricPort metricPort;

    @Override
    public void saveDiskMetric(DiskRecord diskRecord) {
        metricPort.saveDiskMetrics(diskRecord);
    }
}
