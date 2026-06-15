package com.pipeline.observer.domain.ports.inbound.port;

import com.pipeline.observer.domain.model.RamRecord;

public interface MetricEventPort {

    void handleMetricCreated(RamRecord ramRecord);
}
