package com.pipeline.observer.application.memorymanagment;

import com.pipeline.observer.domain.ports.inbound.GetMemoryMetricsUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPorts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoryJsonService implements GetMemoryMetricsUseCase {

    private final MetricPorts metricPorts;

    public MemoryRecord calculateRuntime(){

        Runtime runtime = Runtime.getRuntime();

        int processors = runtime.availableProcessors();

        long totalMemoryMb = runtime.totalMemory() / (1024*1024);
        long freeMemoryMb = runtime.freeMemory() / (1024*1024);
        long usedMemoryMb = totalMemoryMb - freeMemoryMb;

        metricPorts.saveMetrics(processors, freeMemoryMb, totalMemoryMb, usedMemoryMb);
        return new MemoryRecord(processors, totalMemoryMb, freeMemoryMb, usedMemoryMb);
    }
}
