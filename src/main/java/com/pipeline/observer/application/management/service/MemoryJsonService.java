package com.pipeline.observer.application.memorymanagment.service;

import com.pipeline.observer.domain.model.CpuRecord;
import com.pipeline.observer.domain.model.MemoryRecord;
import com.pipeline.observer.domain.ports.inbound.GetMemoryMetricsUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
@RequiredArgsConstructor
public class MemoryJsonService implements GetMemoryMetricsUseCase {

    private final MetricPort metricPort;

    public MemoryRecord calculateMemoryRuntime(){

        Runtime runtime = Runtime.getRuntime();


        long totalMemoryMb = runtime.totalMemory() / (1024*1024);
        long freeMemoryMb = runtime.freeMemory() / (1024*1024);
        long usedMemoryMb = totalMemoryMb - freeMemoryMb;

        metricPort.saveSystemMetrics(freeMemoryMb, totalMemoryMb, usedMemoryMb);
        return new MemoryRecord(totalMemoryMb, freeMemoryMb, usedMemoryMb);
    }

    public CpuRecord calculateCpuRuntime(){
        ManagementFactory
    }
}
