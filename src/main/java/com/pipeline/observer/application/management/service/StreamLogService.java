package com.pipeline.observer.application.management.service;

import com.pipeline.observer.domain.ports.inbound.usecase.StreamLogUseCase;
import com.pipeline.observer.infrastructure.inbound.rest.dto.LogDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class StreamLogService implements StreamLogUseCase {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<SseEmitter>();

    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
    }

    @Override
    public void streamLogs(LogDTO logDTO){

        for(SseEmitter emitter : emitters){
            try {
                emitter.send(logDTO);
            } catch (Exception e){
                emitters.remove(emitter);
            }
        }
    }
}
