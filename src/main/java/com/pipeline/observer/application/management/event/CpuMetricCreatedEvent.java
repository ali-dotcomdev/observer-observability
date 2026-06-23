package com.pipeline.observer.application.management.event;

import com.pipeline.observer.domain.model.CpuRecord;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CpuMetricCreatedEvent extends ApplicationEvent {

    private final CpuRecord cpuRecord;

    public CpuMetricCreatedEvent(Object source, CpuRecord cpuRecord){
        super(source);
        this.cpuRecord = cpuRecord;
    }
}
