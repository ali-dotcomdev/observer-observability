package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.domain.model.FastMetricsPack;

public interface FastMetricsUseCase {

    FastMetricsPack calculateFastMetrics();
}
