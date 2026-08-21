package com.pipeline.observer.infrastructure.outbound.database.adapter.retention;

import com.pipeline.observer.domain.ports.outbound.retention.LogRetentionPort;
import com.pipeline.observer.infrastructure.outbound.database.repository.ApplicationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresLogRetentionAdapter implements LogRetentionPort {

    private final ApplicationLogRepository repository;

    @Override
    public void deleteLogsOlderThan(LocalDateTime cutoffDate){
        repository.deleteByTimestampBefore(cutoffDate);
    }
}
