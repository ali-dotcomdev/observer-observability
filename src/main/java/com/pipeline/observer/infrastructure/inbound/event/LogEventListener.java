package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.LogCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.log.SaveLogUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.log.StreamLogUseCase;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogEventListener {

    private final StreamLogUseCase streamLogUseCase;
    private final SaveLogUseCase saveLogUseCase;

    @Async
    @EventListener
    public void saveLogs(LogCreatedEvent event){
        saveLogUseCase.saveLog(event.getLogDTO());
    }

    @EventListener
    public void handleLogsStream(LogCreatedEvent event){
        streamLogUseCase.streamLogs(event.getLogDTO());
    }
}
