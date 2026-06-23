package com.pipeline.observer.domain.ports.inbound.usecase.ram;

import com.pipeline.observer.domain.model.RamRecord;

public interface StreamRamUseCase {

    void streamMetrics(RamRecord record);
}
