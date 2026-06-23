package com.pipeline.observer.domain.ports.inbound.usecase.cpu;

import com.pipeline.observer.domain.model.CpuRecord;

public interface CpuMonitorUseCase {

    CpuRecord measureCpu();
}
