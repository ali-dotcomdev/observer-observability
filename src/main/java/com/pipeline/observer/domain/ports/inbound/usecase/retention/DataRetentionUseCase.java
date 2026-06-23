package com.pipeline.observer.domain.ports.inbound.usecase.retention;

public interface DataRetentionUseCase {

    void purgeHistoricalData();
}
