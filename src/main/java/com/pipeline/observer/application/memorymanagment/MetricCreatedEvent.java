package com.pipeline.observer.application.memorymanagment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MetricCreatedEvent extends ApplicationEvent {

    private final MemoryRecord memoryRecord;

    public MetricCreatedEvent(Object source, MemoryRecord memoryRecord){
        super(source);
        this.memoryRecord = memoryRecord;
    }
}
