package com.pipeline.observer.application.memorymanagment;

import com.pipeline.observer.domain.ports.inbound.StreamMetricUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class StreamMetricService implements StreamMetricUseCase{

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>(); //for thread-safety

    public void streamMetrics(MemoryRecord record){
        for(SseEmitter emitter : emitters){
            try {
                emitter.send(record);
            }catch (Exception e){
                emitters.remove(record);
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
