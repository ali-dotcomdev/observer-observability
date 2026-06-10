package com.pipeline.observer.infrastructure.inbound.rest;

import com.pipeline.observer.application.memorymanagment.MemoryJsonService;
import com.pipeline.observer.domain.ports.inbound.GetMemoryMetricsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pipeline.observer.application.memorymanagment.MemoryRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final GetMemoryMetricsUseCase getMemoryMetricsUseCase;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>(); //for thread-safety

    @GetMapping("/health")
    public MemoryRecord getHealthStatus(){
        return getMemoryMetricsUseCase.calculateRuntime();
    }

    @GetMapping("/stream/metrics")
    public SseEmitter getEmitters(){
        SseEmitter emitter = new SseEmitter(-1L);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        return emitter;
    }

    @Scheduled(fixedRate = 2000)
    public void publishMetrics(){
        var memoryRecord = getMemoryMetricsUseCase.calculateRuntime();

        for (var e : emitters){
            try {
                e.send(memoryRecord);
            } catch (Exception ex){
                emitters.remove(ex);
            }
        }
    }
}
