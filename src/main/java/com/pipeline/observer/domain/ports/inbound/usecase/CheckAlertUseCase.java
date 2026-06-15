package com.pipeline.observer.domain.ports.inbound.usecase;

import com.pipeline.observer.domain.model.FastMetricsPack;

public interface CheckAlertUseCase {
    void checkAlert(FastMetricsPack fastMetricsPack);
}
