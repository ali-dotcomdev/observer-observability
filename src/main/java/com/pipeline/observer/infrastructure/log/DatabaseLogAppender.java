package com.pipeline.observer.infrastructure.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.pipeline.observer.infrastructure.log.context.ApplicationContextUtils;
import com.pipeline.observer.infrastructure.outbound.database.ApplicationLogEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.ApplicationLogRepository;
import lombok.Builder;

import java.time.Instant;
import java.time.ZoneId;

public class DatabaseLogAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject) {

        if(ApplicationContextUtils.getContext() == null) return;

        try {
            var repository = ApplicationContextUtils.getBean(ApplicationLogRepository.class);

            ApplicationLogEntity logEntity = ApplicationLogEntity.builder()
                    .logLevel(eventObject.getLevel().toString())
                    .message(eventObject.getFormattedMessage())
                    .timestamp(Instant.ofEpochMilli(eventObject.getTimeStamp())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime())
                    .build();
            repository.save(logEntity);
        }catch (Exception e){
            System.err.println("Log DB'ye yazılamadı " + e.getMessage());
        }
    }
}
