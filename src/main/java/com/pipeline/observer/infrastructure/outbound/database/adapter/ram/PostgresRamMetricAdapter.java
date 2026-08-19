package com.pipeline.observer.infrastructure.outbound.database.adapter.ram;

import com.pipeline.observer.domain.model.RamRecord;
import com.pipeline.observer.domain.ports.outbound.ram.SaveRamMetricPort;
import com.pipeline.observer.infrastructure.outbound.database.entity.SystemRamEntity;
import com.pipeline.observer.infrastructure.outbound.database.repository.SystemRamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostgresRamMetricAdapter implements SaveRamMetricPort {

    private final SystemRamRepository systemRamRepository;

    @Override
    public void saveRamMetrics(RamRecord ramRecord) {
        SystemRamEntity systemRamEntity = SystemRamEntity.builder()
                .freeMemoryMb(ramRecord.freeMemoryMb())
                .totalMemoryMb(ramRecord.totalMemoryMb())
                .usedMemoryMb(ramRecord.usedMemoryMb())
                .timestamp(LocalDateTime.now())
                .build();
        systemRamRepository.save(systemRamEntity);
    }
}
