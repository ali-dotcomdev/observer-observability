package com.pipeline.observer.domain.ports.outbound.retention;

import java.time.LocalDateTime;

public interface MetricRetentionPort {

    void deleteMetricsOlderThan(LocalDateTime cutoffDate);
}
