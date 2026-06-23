package com.pipeline.observer.application.management.service.cpu;

import com.pipeline.observer.domain.model.CpuRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.cpu.CpuMonitorUseCase;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
public class CpuMonitorService implements CpuMonitorUseCase {

    @Override
    public CpuRecord measureCpu(){

        var cpuRecords = ManagementFactory.getPlatformMXBean(com.sun.management.OperatingSystemMXBean.class);
        double cpuLoad  = cpuRecords.getCpuLoad()*100;
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        return new CpuRecord(cpuLoad, availableProcessors);
    }
}
