package com.pipeline.observer.application.management.event;

import com.pipeline.observer.domain.model.DiskRecord;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DiskMetricCreatedEvent extends ApplicationEvent {

    private final DiskRecord diskRecord;

    public DiskMetricCreatedEvent(Object source, DiskRecord diskRecord){
        super(source);
        this.diskRecord = diskRecord;
    }
}
