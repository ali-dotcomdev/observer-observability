package com.pipeline.observer.domain.ports.inbound;

import com.pipeline.observer.domain.model.MemoryRecord;

public interface CheckAlertUseCase {
    void checkAlert(MemoryRecord memoryRecord);
}
