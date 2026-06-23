package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.LogCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.log.StreamLogUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogEventListener {

    private final StreamLogUseCase streamLogUseCase;

    @EventListener
    public void handleLogsStream(LogCreatedEvent event){
        streamLogUseCase.streamLogs(event.getLogDTO());
    }
}
