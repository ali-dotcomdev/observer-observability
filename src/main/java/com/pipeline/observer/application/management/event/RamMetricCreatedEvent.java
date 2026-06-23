package com.pipeline.observer.application.management.event;

import com.pipeline.observer.domain.model.RamRecord;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RamMetricCreatedEvent extends ApplicationEvent {

    private final RamRecord ramRecord;

    public RamMetricCreatedEvent(Object source, RamRecord ramRecord){
        super(source);
        this.ramRecord = ramRecord;
    }
}
