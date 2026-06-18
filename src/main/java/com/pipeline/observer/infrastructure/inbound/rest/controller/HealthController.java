package com.pipeline.observer.infrastructure.inbound.rest.controller;

import com.pipeline.observer.application.management.event.DiskMetricCreatedEvent;
import com.pipeline.observer.application.management.event.FastMetricsCreatedEvent;
import com.pipeline.observer.application.management.service.StreamLogService;
import com.pipeline.observer.application.management.service.StreamMetricService;
import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;
import com.pipeline.observer.domain.ports.inbound.usecase.DiskMetricUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.FastMetricsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pipeline.observer.domain.model.RamRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final FastMetricsUseCase fastMetricsUseCase;
    private final DiskMetricUseCase diskMetricUseCase;
    private final ApplicationEventPublisher eventPublisher;
    private final StreamMetricService streamMetricService;
    private final StreamLogService streamLogService;

    @GetMapping("/health")
    public RamRecord getHealthStatus(){
        return fastMetricsUseCase.calculateFastMetrics().ramRecord();
    }

    @GetMapping("/stream/logs")
    public SseEmitter streamLogs(){
        SseEmitter emitter = new SseEmitter(-1L);
        streamLogService.addEmitter(emitter);
        return emitter;
    }

    @GetMapping("/stream/metrics")
    public SseEmitter getEmitters(){
        SseEmitter emitter = new SseEmitter(-1L);

        streamMetricService.addEmitter(emitter);

        return emitter;
    }

    @Scheduled(fixedRate = 15000)
    public void publishMetrics(){
        FastMetricsPack fast = fastMetricsUseCase.calculateFastMetrics();

        FastMetricsCreatedEvent fastEvent = new FastMetricsCreatedEvent(this, fast);
        eventPublisher.publishEvent(fastEvent);
    }

    @Scheduled(fixedRate = 15000)
    public void publishDiskMetric(){
        DiskRecord disk = diskMetricUseCase.calculateDiskRecord();

        DiskMetricCreatedEvent diskEvent = new DiskMetricCreatedEvent(this, disk);
        eventPublisher.publishEvent(diskEvent);
    }
}
