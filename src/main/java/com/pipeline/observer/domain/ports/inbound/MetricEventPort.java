package com.pipeline.observer.domain.ports.inbound;

import com.pipeline.observer.application.memorymanagment.MemoryRecord;

public interface MetricEventPort {

    void handleMetricCreated(MemoryRecord memoryRecord);
}
