package com.pipeline.observer.infrastructure.inbound.scheduler;

import com.pipeline.observer.application.management.event.CpuMetricCreatedEvent;
import com.pipeline.observer.domain.model.CpuRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.cpu.CpuMonitorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CpuMetricScheduler {

    private final CpuMonitorUseCase cpuMonitorUseCase;
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(fixedRate = 15000)
    public void scheduleCpuMeasurement(){

        CpuRecord cpuRecord = cpuMonitorUseCase.measureCpu();
        CpuMetricCreatedEvent createdEvent = new CpuMetricCreatedEvent(this, cpuRecord);

        eventPublisher.publishEvent(createdEvent);
    }
}
