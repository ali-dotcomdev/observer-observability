package com.pipeline.observer.domain.ports.outbound.log;

import com.pipeline.observer.application.management.event.LogCreatedEvent;
import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;

public interface SaveLogPort {

    void saveLogs(LogDTO dto);
}
