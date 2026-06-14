package com.pipeline.observer.application.management.service;

import com.pipeline.observer.domain.model.*;
import com.pipeline.observer.domain.ports.inbound.DiskMetricUseCase;
import com.pipeline.observer.domain.ports.inbound.FastMetricsUseCase;
import com.pipeline.observer.domain.ports.inbound.GetMemoryMetricsUseCase;
import com.pipeline.observer.domain.ports.outbound.MetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemoryJsonService implements FastMetricsUseCase, DiskMetricUseCase {

    private CpuRecord measureCpu(){
        var cpuRecords = ManagementFactory.getPlatformMXBean(com.sun.management.OperatingSystemMXBean.class);
        double cpuLoad = cpuRecords.getCpuLoad()*100;
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        return new CpuRecord(cpuLoad, availableProcessors);
    }

    private MemoryRecord measureMemory(){
        Runtime runtime = Runtime.getRuntime();
        long totalMemoryMb = runtime.totalMemory() / (1024*1024);
        long freeMemoryMb = runtime.freeMemory() / (1024*1024);
        long usedMemoryMb = totalMemoryMb - freeMemoryMb;

        return new MemoryRecord(totalMemoryMb, freeMemoryMb, usedMemoryMb);
    }

    @Override
    public DiskRecord calculateDiskRecord() {

        File root = new File("/");
        long totalSpaceGb = root.getTotalSpace() / (1024*1024*1024);
        long freeSpaceGb = root.getFreeSpace() / (1024*1024*1024);
        long usedSpaceGb = totalSpaceGb - freeSpaceGb;

        DiskRecord diskRecord = new DiskRecord(totalSpaceGb, freeSpaceGb, usedSpaceGb);

        return diskRecord;
    }

    @Override
    public FastMetricsPack calculateFastMetrics() {

        MemoryRecord memoryRecord = measureMemory();
        CpuRecord cpuRecord = measureCpu();

        FastMetricsPack fastMetricsPack = new FastMetricsPack(memoryRecord, cpuRecord);

        return fastMetricsPack;
    }
}
