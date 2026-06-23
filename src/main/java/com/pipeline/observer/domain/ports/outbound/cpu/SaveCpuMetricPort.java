package com.pipeline.observer.domain.ports.outbound.cpu;

import com.pipeline.observer.domain.model.CpuRecord;

public interface SaveCpuMetricPort {
    void saveCpuMetrics(CpuRecord cpuRecord);
}
