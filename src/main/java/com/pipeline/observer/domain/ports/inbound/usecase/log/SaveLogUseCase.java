package com.pipeline.observer.domain.ports.inbound.usecase.log;

import com.pipeline.observer.application.management.event.LogCreatedEvent;
import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;

public interface SaveLogUseCase {

    void saveLog(LogDTO dto);
}
