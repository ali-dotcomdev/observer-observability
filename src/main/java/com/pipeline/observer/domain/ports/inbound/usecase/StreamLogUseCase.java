package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;

public interface StreamLogUseCase {
    void streamLogs(LogDTO logDTO);
}
