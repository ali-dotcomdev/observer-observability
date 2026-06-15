package com.pipeline.observer.application.management.event;

import com.pipeline.observer.domain.model.FastMetricsPack;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FastMetricsCreatedEvent extends ApplicationEvent {

    private final FastMetricsPack fastMetricsPack;

    public FastMetricsCreatedEvent(Object source, FastMetricsPack fastMetricsPack){
        super(source);
        this.fastMetricsPack = fastMetricsPack;
    }
}
