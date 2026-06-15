package com.pipeline.observer.application.management.service;

import com.pipeline.observer.domain.model.FastMetricsPack;
import com.pipeline.observer.domain.ports.inbound.usecase.CheckAlertUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertingEngineService implements CheckAlertUseCase {

    @Override
    public void checkAlert(FastMetricsPack fastMetricsPack){

        long usedMemory = fastMetricsPack.ramRecord().usedMemoryMb();
        long totalMemory = fastMetricsPack.ramRecord().totalMemoryMb();
        long memoryUsagePercentage = (usedMemory * 100) / totalMemory;

        if(memoryUsagePercentage > 85){
            log.warn("CRITICAL: High Memory Usage Detected! RAM Usage: {}%", memoryUsagePercentage);
        }

        double cpuUsagePercentage = fastMetricsPack.cpuRecord().cpuLoad();

        if(cpuUsagePercentage > 90){
            log.warn("CRITICAL: High Cpu Usage Detected! CPU Usage: {}%", cpuUsagePercentage);
        }

    }

}
