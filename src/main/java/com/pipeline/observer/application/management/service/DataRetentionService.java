package com.pipeline.observer.application.management.service;

import com.pipeline.observer.domain.ports.inbound.usecase.DataRetentionUseCase;
import com.pipeline.observer.domain.ports.outbound.LogRetentionPort;
import com.pipeline.observer.domain.ports.outbound.MetricRetentionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DataRetentionService implements DataRetentionUseCase {

    private final MetricRetentionPort metricRetentionPort;
    private final LogRetentionPort logRetentionPort;

    public void purgeHistoricalData(){
        LocalDateTime cutoffDateForLogs = LocalDateTime.now().minusDays(7);
        LocalDateTime cutoffDateForMetrics = LocalDateTime.now().minusDays(2);

        metricRetentionPort.deleteMetricsOlderThan(cutoffDateForMetrics);
        logRetentionPort.deleteLogsOlderThan(cutoffDateForLogs);
    }
}
