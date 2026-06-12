package com.pipeline.observer.infrastructure.inbound.scheduler;

import com.pipeline.observer.domain.ports.outbound.LogRetentionPort;
import com.pipeline.observer.domain.ports.outbound.RetentionLogPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataRetentionScheduler {

    private final RetentionLogPort retentionLogPort;
    private final LogRetentionPort logRetentionPort;

    @Scheduled(cron = "0 0 3 * * ?")
    public void cleaner(){
        LocalDateTime metricCutoff = LocalDateTime.now().minusDays(2);
        retentionLogPort.deleteMetricsOlderThan(metricCutoff);
        log.info("Metrics older than {} have been purged.", metricCutoff);

        LocalDateTime logCutoff = LocalDateTime.now().minusDays(7);
        logRetentionPort.deleteLogsOlderThan(logCutoff);
        log.info("Application logs older than {} have been purged.", logCutoff);

        log.info("Midnight database cleanup optimization completed successfully.");
    }
}
