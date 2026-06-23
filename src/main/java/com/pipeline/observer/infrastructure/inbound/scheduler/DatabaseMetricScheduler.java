package com.pipeline.observer.infrastructure.inbound.scheduler;

import com.pipeline.observer.application.management.event.DatabaseMetricCreatedEvent;
import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.database.DatabaseMetricUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseMetricScheduler {

    private final DatabaseMetricUseCase databaseMetricUseCase;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 15000)
    @Async
    public void databaseMetricScheduler(){

        DatabaseMetricRecord dbRecord = databaseMetricUseCase.calculateDatabaseMetrics();
        DatabaseMetricCreatedEvent databaseMetricCreatedEvent = new DatabaseMetricCreatedEvent(this, dbRecord);
        applicationEventPublisher.publishEvent(databaseMetricCreatedEvent);
    }

}
