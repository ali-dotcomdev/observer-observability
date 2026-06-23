package com.pipeline.observer.application.management.service.ram;

import com.pipeline.observer.domain.model.RamRecord;
import com.pipeline.observer.domain.ports.inbound.usecase.ram.RamMonitorUseCase;
import org.springframework.stereotype.Service;

@Service
public class RamMonitorService implements RamMonitorUseCase {

    @Override
    public RamRecord measureRam(){

        Runtime runtime = Runtime.getRuntime();
        long totalMemoryMb = runtime.totalMemory() / (1024*1024);
        long freeMemoryMb = runtime.freeMemory() / (1024*1024);
        long usedMemoryMb = totalMemoryMb - freeMemoryMb;

        return new RamRecord(totalMemoryMb, freeMemoryMb, usedMemoryMb);
    }
}
