package com.pipeline.observer.application.management.service.cpu;

import com.pipeline.observer.domain.model.CpuRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.cpu.SaveCpuMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.cpu.SaveCpuMetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveCpuMetricService implements SaveCpuMetricUseCase {

    private final SaveCpuMetricPort cpuMetricPort;

    @Override
    public void saveCpuMetrics(CpuRecord cpuRecord) {
        cpuMetricPort.saveCpuMetrics(cpuRecord);
    }
}
