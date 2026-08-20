package com.pipeline.observer.application.management.service.ram;

import com.pipeline.observer.domain.model.RamRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.ram.SaveRamMetricUseCase;
import com.pipeline.observer.domain.ports.outbound.ram.SaveRamMetricPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveRamMetricService implements SaveRamMetricUseCase {

    private final SaveRamMetricPort saveRamMetricPort;

    @Override
    public void saveRamMetrics(RamRecord ramRecord){
        saveRamMetricPort.saveRamMetrics(ramRecord);
    }
}
