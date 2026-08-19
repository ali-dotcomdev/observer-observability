package com.pipeline.observer.domain.ports.inbound.usecase.cpu;

import com.pipeline.observer.domain.model.CpuRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamCpuUseCase {

    void streamCpuMetrics(CpuRecord record);
    void addEmitter(SseEmitter emitter);
}
