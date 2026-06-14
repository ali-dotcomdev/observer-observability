package com.pipeline.observer.application.memorymanagment.service;

import com.pipeline.observer.domain.model.MemoryRecord;
import com.pipeline.observer.domain.model.SystemMetricSnapshot;
import com.pipeline.observer.domain.ports.inbound.SaveMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveMetricService implements SaveMetricUseCase {

    private final MetricPort metricPort;

    @Override
    public void saveMetrics(SystemMetricSnapshot systemMetricSnapshot){

        metricPort.saveSystemMetrics(
                systemMetricSnapshot
        );
    }
}
