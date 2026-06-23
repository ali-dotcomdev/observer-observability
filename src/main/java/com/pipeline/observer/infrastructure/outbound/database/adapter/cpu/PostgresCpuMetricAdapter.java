package com.pipeline.observer.infrastructure.outbound.database.adapter.cpu;

import com.pipeline.observer.domain.model.CpuRecord;
import com.pipeline.observer.domain.ports.outbound.cpu.SaveCpuMetricPort;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemCpuEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemCpuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresCpuMetricAdapter implements SaveCpuMetricPort {

    private final SystemCpuRepository systemCpuRepository;

    @Override
    public void saveCpuMetrics(CpuRecord cpuRecord) {

        SystemCpuEntity systemCpuEntity = SystemCpuEntity.builder()
                .availableProcessors(cpuRecord.availableProcessors())
                .cpuLoad(cpuRecord.cpuLoad())
                .timestamp(LocalDateTime.now())
                .build();
        systemCpuRepository.save(systemCpuEntity);
    }
}
