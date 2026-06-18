package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.DatabaseMetricCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.SaveDatabaseMetricsUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.StreamDatabaseMetricsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseMetricListener {

    private final StreamDatabaseMetricsUseCase streamDatabaseMetricsUseCase;
    private final SaveDatabaseMetricsUseCase saveDatabaseMetricsUseCase;

    @EventListener
    public void handleDatabaseMetricsStream(DatabaseMetricCreatedEvent event){
        streamDatabaseMetricsUseCase.streamMetrics(event.getDatabaseMetricRecord());
    }

    @EventListener
    @Async
    public void handleDatabaseMetricsSave(DatabaseMetricCreatedEvent event){
        saveDatabaseMetricsUseCase.saveDatabaseMetrics(event.getDatabaseMetricRecord());
    }
}
