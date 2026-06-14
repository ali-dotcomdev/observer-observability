package com.pipeline.observer.application.memorymanagment.service;

import com.pipeline.observer.domain.model.MemoryRecord;
import com.pipeline.observer.domain.model.SystemMetricSnapshot;
import com.pipeline.observer.domain.ports.inbound.StreamMetricUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class StreamMetricService implements StreamMetricUseCase{

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>(); //for thread-safety

    @Override
    public void streamMetrics(SystemMetricSnapshot systemMetricSnapshot){
        for(SseEmitter emitter : emitters){
            try {
                emitter.send(systemMetricSnapshot);
            }catch (Exception e){
                emitters.remove(systemMetricSnapshot);
            }
        }
    }

    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
    }
}
