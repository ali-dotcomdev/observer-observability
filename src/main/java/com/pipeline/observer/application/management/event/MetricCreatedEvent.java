package com.pipeline.observer.application.memorymanagment.event;

import com.pipeline.observer.domain.model.MemoryRecord;
import com.pipeline.observer.domain.model.SystemMetricSnapshot;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MetricCreatedEvent extends ApplicationEvent {

    private final SystemMetricSnapshot systemMetricSnapshot;

    public MetricCreatedEvent(Object source, SystemMetricSnapshot systemMetricSnapshot){
        super(source);
        this.systemMetricSnapshot = systemMetricSnapshot;
    }
}
