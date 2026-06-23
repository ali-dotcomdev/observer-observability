package com.pipeline.observer.domain.ports.inbound.usecase.log;

import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamLogUseCase {
    void addEmitter(SseEmitter emitter);
    void streamLogs(LogDTO logDTO);
}
