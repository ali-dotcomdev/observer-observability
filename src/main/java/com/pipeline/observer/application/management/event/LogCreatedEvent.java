package com.pipeline.observer.application.management.event;

import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LogCreatedEvent extends ApplicationEvent {

    private final LogDTO logDTO;

    public LogCreatedEvent(Object source, LogDTO logDTO) {
        super(source);
        this.logDTO = logDTO;
    }
}
