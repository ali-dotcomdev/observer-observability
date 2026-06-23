package com.pipeline.observer.application.management.service.cpu;

import com.pipeline.observer.domain.model.CpuRecord;
import com.pipeline.observer.domain.model.RamRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.cpu.StreamCpuUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class StreamCpuService implements StreamCpuUseCase {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
    }

    @Override
    public void streamMetrics(CpuRecord record) {
        for(SseEmitter emitter : emitters){
            try {
                emitter.send(record);
            }catch (Exception e){
                emitters.remove(emitter);
            }
        }
    }
}
