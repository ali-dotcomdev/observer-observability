package com.pipeline.observer.domain.ports.outbound;

import java.time.LocalDateTime;

public interface RetentionLogPort {

    void deleteMetricsOlderThan(LocalDateTime cutoffDate);
}
