package com.pipeline.observer.domain.ports.inbound.usecase.database;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamDatabaseMetricsUseCase {

    void addEmitter(SseEmitter emitter);
    void streamMetrics(DatabaseMetricRecord databaseMetricRecord);
}
