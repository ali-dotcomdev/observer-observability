package com.pipeline.observer.infrastructure.inbound.rest.controller;

import com.pipeline.observer.domain.ports.inbound.usecase.*;
import com.pipeline.observer.domain.ports.inbound.usecase.database.StreamDatabaseMetricsUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.log.StreamLogUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pipeline.observer.domain.model.RamRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final FastMetricsUseCase fastMetricsUseCase;
    private final StreamMetricUseCase streamMetricUseCase;
    private final StreamLogUseCase streamLogUseCase;
    private final StreamDatabaseMetricsUseCase streamDatabaseMetricsUseCase;

    @GetMapping("/stream/databasemetrics")
    public SseEmitter streamDatabaseMetrics(){
        SseEmitter emitter = new SseEmitter(-1L);
        streamDatabaseMetricsUseCase.addEmitter(emitter);
        return emitter;
    }

    @GetMapping("/health")
    public RamRecord getHealthStatus(){
        return fastMetricsUseCase.calculateFastMetrics().ramRecord();
    }

    @GetMapping("/stream/logs")
    public SseEmitter streamLogs(){
        SseEmitter emitter = new SseEmitter(-1L);
        streamLogUseCase.addEmitter(emitter);
        return emitter;
    }

    @GetMapping("/stream/metrics")
    public SseEmitter getEmitters(){
        SseEmitter emitter = new SseEmitter(-1L);

        streamMetricUseCase.addEmitter(emitter);

        return emitter;
    }
}
