package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.memorymanagment.MemoryRecord;
import com.pipeline.observer.application.memorymanagment.MetricCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.MetricEventPort;
import com.pipeline.observer.domain.ports.inbound.SaveMetricUseCase;
import com.pipeline.observer.domain.ports.inbound.StreamMetricUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricEventListener {

    private final SaveMetricUseCase saveMetricUseCase;
    private final StreamMetricUseCase streamMetricUseCase;

    @EventListener
    @Async
    public void handleDatabaseSave(MetricCreatedEvent event){
        saveMetricUseCase.saveMetrics(event.getMemoryRecord());
    }

    @EventListener
    public void handleSseStreaming(MetricCreatedEvent event){
        streamMetricUseCase.streamMetrics(event.getMemoryRecord());
    }
}
