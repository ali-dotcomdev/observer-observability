package com.pipeline.observer.infrastructure.inbound.scheduler;

import com.pipeline.observer.application.management.event.FastMetricsCreatedEvent;
import com.pipeline.observer.domain.model.FastMetricsPack;
import com.pipeline.observer.domain.ports.inbound.usecase.FastMetricsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FastMetricScheduler {

    private final FastMetricsUseCase fastMetricsUseCase;
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(fixedRate = 15000)
    public void publishMetrics(){
        FastMetricsPack fast = fastMetricsUseCase.calculateFastMetrics();

        FastMetricsCreatedEvent fastEvent = new FastMetricsCreatedEvent(this, fast);
        eventPublisher.publishEvent(fastEvent);
    }
}
