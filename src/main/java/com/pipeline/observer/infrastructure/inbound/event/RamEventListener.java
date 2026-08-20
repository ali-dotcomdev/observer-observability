package com.pipeline.observer.infrastructure.inbound.event;

import com.pipeline.observer.application.management.event.RamMetricCreatedEvent;
import com.pipeline.observer.domain.model.RamRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.ram.SaveRamMetricUseCase;
import com.pipeline.observer.domain.ports.inbound.usecase.ram.StreamRamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RamEventListener {

    private final StreamRamUseCase streamRamUseCase;
    private final SaveRamMetricUseCase saveRam;

    @EventListener
    public void streamRamMetrics(RamMetricCreatedEvent event){
        streamRamUseCase.streamMetrics(event.getRamRecord());
    }

    @Async
    @EventListener
    public void saveRamMetrics(RamMetricCreatedEvent event){

        saveRam.saveRamMetrics(event.getRamRecord());
    }
}
