package com.pipeline.observer.application.management.service;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;
import com.pipeline.observer.domain.ports.inbound.usecase.SaveMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveMetricService implements SaveMetricUseCase {

    private final MetricPort metricPort;

    @Override
    public void saveFastMetrics(FastMetricsPack fastMetricsPack) {

        metricPort.saveFastMetrics(fastMetricsPack);
    }

    @Override
    public void saveDiskMetric(DiskRecord diskRecord) {

        metricPort.saveDiskMetrics(diskRecord);
    }
}
