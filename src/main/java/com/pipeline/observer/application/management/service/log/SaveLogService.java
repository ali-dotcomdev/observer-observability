package com.pipeline.observer.application.management.service.log;

import com.pipeline.observer.application.management.event.LogCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.log.SaveLogUseCase;
import com.pipeline.observer.domain.ports.outbound.log.SaveLogPort;
import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveLogService implements SaveLogUseCase{

    private final SaveLogPort logPort;

    @Override
    public void saveLog(LogDTO dto) {
        logPort.saveLogs(dto);
    }
}
