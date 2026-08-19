package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.CpuMetricCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.cpu.StreamCpuUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CpuEventListener {

    private final StreamCpuUseCase streamCpuUseCase;

    @EventListener
    public void streamCpuMetric(CpuMetricCreatedEvent event){
        streamCpuUseCase.streamCpuMetrics(event.getCpuRecord());
    }
}
