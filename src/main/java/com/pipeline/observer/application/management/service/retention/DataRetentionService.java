package com.pipeline.observer.application.management.service.retention;

import com.pipeline.observer.domain.ports.inbound.usecase.retention.DataRetentionUseCase;
import com.pipeline.observer.domain.ports.outbound.retention.LogRetentionPort;
import com.pipeline.observer.domain.ports.outbound.retention.MetricRetentionPort;
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
