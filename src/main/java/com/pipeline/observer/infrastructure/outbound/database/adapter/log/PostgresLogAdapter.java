package com.pipeline.observer.infrastructure.outbound.database.adapter.log;

import com.pipeline.observer.application.management.event.LogCreatedEvent;
import com.pipeline.observer.domain.ports.outbound.log.SaveLogPort;
import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;
import com.pipeline.observer.infrastructure.log.context.ApplicationContextUtils;
import com.pipeline.observer.infrastructure.outbound.database.entity.ApplicationLogEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.ApplicationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class PostgresLogAdapter implements SaveLogPort {

    private final ApplicationLogRepository repository;

    @Override
    public void saveLogs(LogDTO dto) {

        ApplicationLogEntity logEntity = ApplicationLogEntity.builder()
                .logLevel(dto.getLogLevel())
                .message(dto.getMessage())
                .timestamp(dto.getTimestamp())
                .build();
        repository.save(logEntity);
    }
}
