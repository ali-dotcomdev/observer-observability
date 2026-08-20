package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.CpuMetricCreatedEvent;
import com.pipeline.observer.domain.ports.inbound.usecase.cpu.SaveCpuMetricUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.cpu.StreamCpuUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
@RequiredArgsConstructor
public class CpuEventListener {

    private final StreamCpuUseCase streamCpuUseCase;
    private final SaveCpuMetricUseCase saveCpu;

    @EventListener
    public void streamCpuMetrics(CpuMetricCreatedEvent event){
        streamCpuUseCase.streamCpuMetrics(event.getCpuRecord());
    }

    @EventListener
    public void saveCpuMetrics(CpuMetricCreatedEvent event){
        saveCpu.saveCpuMetrics(event.getCpuRecord());
    }
}
