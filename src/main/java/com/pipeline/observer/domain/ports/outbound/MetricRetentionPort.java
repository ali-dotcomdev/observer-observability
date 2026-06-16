package com.pipeline.observer.domain.ports.outbound;

import java.time.LocalDateTime;

public interface MetricRetentionPort {

    void deleteMetricsOlderThan(LocalDateTime cutoffDate);
}
