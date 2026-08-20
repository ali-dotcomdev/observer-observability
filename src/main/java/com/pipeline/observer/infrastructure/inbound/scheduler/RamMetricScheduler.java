package com.pipeline.observer.infrastructure.inbound.scheduler;

import com.pipeline.observer.application.management.event.RamMetricCreatedEvent;
import com.pipeline.observer.domain.model.RamRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.ram.RamMonitorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RamMetricScheduler {

    private final ApplicationEventPublisher eventPublisher;
    private final RamMonitorUseCase ramMonitorUseCase;

    @Scheduled(fixedRate = 15000)
    public void scheduleRamMeasurement(){

        RamRecord ramRecord = ramMonitorUseCase.measureRam();
        RamMetricCreatedEvent ramMetric = new RamMetricCreatedEvent(this, ramRecord);

        eventPublisher.publishEvent(ramMetric);
    }
}
