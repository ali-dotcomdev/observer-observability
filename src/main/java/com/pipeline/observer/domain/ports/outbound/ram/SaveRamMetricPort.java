package com.pipeline.observer.domain.ports.outbound.ram;

import com.pipeline.observer.domain.model.RamRecord;

public interface SaveRamMetricPort {
    void saveRamMetrics(RamRecord ramRecord);
}
