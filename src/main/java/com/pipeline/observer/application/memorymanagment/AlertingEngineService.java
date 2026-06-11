package com.pipeline.observer.application.memorymanagment;

import com.pipeline.observer.domain.ports.inbound.CheckAlertUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertingEngineService implements CheckAlertUseCase {

    @Override
    public void checkAlert(MemoryRecord memoryRecord){
        long usedMemory = memoryRecord.usedMemoryMb();
        long totalMemory = memoryRecord.totalMemoryMb();

        long usagePercentage = (usedMemory * 100) / totalMemory;

        if(usagePercentage > 85){
            log.warn("CRITICAL: High Memory Usage Detected! RAM Usage: {}%", usagePercentage);
        }
    }
}
