package com.pipeline.observer.application.management.service.database;

import com.pipeline.observer.domain.model.DatabaseMetricRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.database.StreamDatabaseMetricsUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class StreamDatabaseMetricsService implements StreamDatabaseMetricsUseCase {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Override
    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
    }

    @Override
    public void streamMetrics(DatabaseMetricRecord databaseMetricRecord){
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(databaseMetricRecord);
            }catch (Exception e){
                emitters.remove(emitter);
            }
        }
    }
}
