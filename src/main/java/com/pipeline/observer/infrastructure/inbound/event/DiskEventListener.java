package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.DiskMetricCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.disk.SaveDiskMetricUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.disk.StreamDiskMetricUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiskEventListener {

    private final StreamDiskMetricUseCase streamDiskMetrics;
    private final SaveDiskMetricUseCase saveDiskMetric;

    @EventListener
    @Async
    public void handleDiskMetricSave(DiskMetricCreatedEvent event){
        saveDiskMetric.saveDiskMetric(event.getDiskRecord());
    }

    @EventListener
    public void handleDiskMetricStream(DiskMetricCreatedEvent event){
        streamDiskMetrics.streamDiskMetrics(event.getDiskRecord());
    }
}
