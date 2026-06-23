package com.pipeline.observer.domain.ports.inbound.usecase.disk;

import com.pipeline.observer.domain.model.DiskRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamDiskMetricUseCase {

    void addEmitter(SseEmitter emitter);
    void streamDiskMetrics(DiskRecord diskRecord);
}
