package com.pipeline.observer.infrastructure.inbound.rest;

import com.pipeline.observer.application.memorymanagment.event.MetricCreatedEvent;
import com.pipeline.observer.application.memorymanagment.service.StreamMetricService;
import com.pipeline.observer.domain.ports.inbound.GetMemoryMetricsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pipeline.observer.domain.model.MemoryRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final GetMemoryMetricsUseCase getMemoryMetricsUseCase;
    private final ApplicationEventPublisher eventPublisher;
    private final StreamMetricService streamMetricService;

    @GetMapping("/health")
    public MemoryRecord getHealthStatus(){
        return getMemoryMetricsUseCase.calculateRuntime();
    }

    @GetMapping("/stream/metrics")
    public SseEmitter getEmitters(){
        SseEmitter emitter = new SseEmitter(-1L);

        streamMetricService.addEmitter(emitter);

        return emitter;
    }

    @Scheduled(fixedRate = 2000)
    public void publishMetrics(){
        var memoryRecord = getMemoryMetricsUseCase.calculateRuntime();

        MetricCreatedEvent event = new MetricCreatedEvent(this, memoryRecord);
        eventPublisher.publishEvent(event);
    }
}
