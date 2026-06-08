package com.pipeline.observer.infrastructure.inbound.rest;

import com.pipeline.observer.application.memorymanagment.MemoryJsonService;
import com.pipeline.observer.domain.ports.inbound.GetMemoryMetricsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pipeline.observer.application.memorymanagment.MemoryRecord;

@RestController
public class HealthController {

    private final GetMemoryMetricsUseCase getMemoryMetricsUseCase;

    public HealthController(GetMemoryMetricsUseCase getMemoryMetricsUseCase){
        this.getMemoryMetricsUseCase = getMemoryMetricsUseCase;
    }

    @GetMapping("/health")
    public MemoryRecord getHealthStatus(){
        return getMemoryMetricsUseCase.calculateRuntime();
    }
}
