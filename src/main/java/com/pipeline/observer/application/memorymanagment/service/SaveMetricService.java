package com.pipeline.observer.application.memorymanagment.service;

import com.pipeline.observer.domain.model.MemoryRecord;
import com.pipeline.observer.domain.ports.inbound.SaveMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPorts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveMetricService implements SaveMetricUseCase {

    private final MetricPorts metricPorts;

    @Override
    public void saveMetrics(MemoryRecord record){

        metricPorts.saveMetrics(
                record.processors(),
                record.freeMemoryMb(),
                record.totalMemoryMb(),
                record.usedMemoryMb()
        );
    }
}
