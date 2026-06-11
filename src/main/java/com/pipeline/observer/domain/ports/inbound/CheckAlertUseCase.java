package com.pipeline.observer.domain.ports.inbound;

import com.pipeline.observer.application.memorymanagment.MemoryRecord;

public interface CheckAlertUseCase {
    void checkAlert(MemoryRecord memoryRecord);
}
