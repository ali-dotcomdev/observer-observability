package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.DiskMetricCreatedEvent;
import com.pipeline.observer.application.management.event.FastMetricsCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.CheckAlertUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.SaveMetricUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.StreamMetricUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricEventListener {

    private final SaveMetricUseCase saveMetricUseCase;
    private final StreamMetricUseCase streamMetricUseCase;
    private final CheckAlertUseCase checkAlertUseCase;

    @EventListener
    @Async
    public void handleFastMetricsSave(FastMetricsCreatedEvent event){
        saveMetricUseCase.saveFastMetrics(event.getFastMetricsPack());
    }

    @EventListener
    public void handleFastMetricsStream(FastMetricsCreatedEvent event){
        streamMetricUseCase.streamMetrics(event.getFastMetricsPack());
    }

    @EventListener
    @Async
    public void handleFastMetricsAlert(FastMetricsCreatedEvent event){
        checkAlertUseCase.checkAlert(event.getFastMetricsPack());
    }

    @EventListener
    @Async
    public void handleDiskMetricSave(DiskMetricCreatedEvent event){
        saveMetricUseCase.saveDiskMetric(event.getDiskRecord());
        streamMetricUseCase.streamDiskMetrics(event.getDiskRecord());
    }
}
