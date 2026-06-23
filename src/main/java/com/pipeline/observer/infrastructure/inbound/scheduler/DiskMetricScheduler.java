package com.pipeline.observer.infrastructure.inbound.scheduler;

import com.pipeline.observer.application.management.event.DiskMetricCreatedEvent;
import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.disk.DiskMetricUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiskMetricScheduler {

    private final ApplicationEventPublisher eventPublisher;
    private final DiskMetricUseCase diskMetricUseCase;

    @Scheduled(fixedRate = 15000)
    public void publishDiskMetric(){
        DiskRecord disk = diskMetricUseCase.calculateDiskRecord();

        DiskMetricCreatedEvent diskEvent = new DiskMetricCreatedEvent(this, disk);
        eventPublisher.publishEvent(diskEvent);
    }
}
