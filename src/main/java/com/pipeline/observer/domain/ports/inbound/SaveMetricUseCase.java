package com.pipeline.observer.domain.ports.inbound;

import com.pipeline.observer.application.memorymanagment.MemoryRecord;
import com.pipeline.observer.application.memorymanagment.MetricCreatedEvent;

public interface SaveMetricUseCase {
    void saveMetrics(MemoryRecord record);
}
