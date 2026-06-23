package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.domain.model.DiskRecord;
import com.pipeline.observer.domain.model.FastMetricsPack;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamMetricUseCase {

    void addEmitter(SseEmitter emitter);
    void streamMetrics(FastMetricsPack fastMetricsPack);
}
