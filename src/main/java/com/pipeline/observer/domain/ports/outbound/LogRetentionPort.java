package com.pipeline.observer.domain.ports.outbound;

import java.time.LocalDateTime;

public interface LogRetentionPort {

    void deleteLogsOlderThan(LocalDateTime cutoffDate);
}
