package com.pipeline.observer.domain.ports.inbound.usecase.ram;

import com.pipeline.observer.domain.model.RamRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamRamUseCase {

    void streamMetrics(RamRecord record);
    void addEmitter(SseEmitter emitter);
}
