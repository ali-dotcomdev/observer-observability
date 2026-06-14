package com.pipeline.observer.domain.ports.inbound;

import com.pipeline.observer.domain.model.SystemMetricSnapshot;

public interface GetMemoryMetricsUseCase {
    SystemMetricSnapshot calculateSystemSnapshot();
}
