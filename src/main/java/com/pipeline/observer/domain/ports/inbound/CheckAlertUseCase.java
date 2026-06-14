package com.pipeline.observer.domain.ports.inbound;

import com.pipeline.observer.domain.model.FastMetricsPack;
import com.pipeline.observer.domain.model.MemoryRecord;
import com.pipeline.observer.domain.model.SystemMetricSnapshot;

public interface CheckAlertUseCase {
    void checkAlert(FastMetricsPack fastMetricsPack);
}
