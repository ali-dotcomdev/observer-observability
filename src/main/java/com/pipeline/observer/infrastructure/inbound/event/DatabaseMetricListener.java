package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.DatabaseMetricCreatedEvent;
import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.StreamDatabaseMetricsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseMetricListener {

    private final StreamDatabaseMetricsUseCase streamDatabaseMetricsUseCase;

    @EventListener
    public void handleDatabaseMetricsStream(DatabaseMetricCreatedEvent event){
        streamDatabaseMetricsUseCase.streamMetrics(event.getDatabaseMetricRecord());
    }
}
