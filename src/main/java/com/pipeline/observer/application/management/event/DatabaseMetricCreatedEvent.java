package com.pipeline.observer.application.management.event;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DatabaseMetricCreatedEvent extends ApplicationEvent {

    private final DatabaseMetricRecord databaseMetricRecord;

    public DatabaseMetricCreatedEvent(Object source, DatabaseMetricRecord databaseMetricRecord){
        super(source);
        this.databaseMetricRecord = databaseMetricRecord;
    }
}
