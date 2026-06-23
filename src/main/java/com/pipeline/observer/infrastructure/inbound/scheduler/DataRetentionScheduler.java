package com.pipeline.observer.infrastructure.inbound.scheduler;

import com.pipeline.observer.domain.ports.inbound.usecase.retention.DataRetentionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DataRetentionScheduler {

    private final DataRetentionUseCase dataRetentionUseCase;

    @Scheduled(cron = "0 0 3 * * ?")
    public void cleaner(){

        dataRetentionUseCase.purgeHistoricalData();
    }
}
