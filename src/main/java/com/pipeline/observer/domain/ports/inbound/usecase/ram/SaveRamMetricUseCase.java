package com.pipeline.observer.domain.ports.inbound.usecase.ram;

import com.pipeline.observer.domain.model.RamRecord;

public interface SaveRamMetricUseCase {
    void saveRamMetrics(RamRecord ramRecord);
}
