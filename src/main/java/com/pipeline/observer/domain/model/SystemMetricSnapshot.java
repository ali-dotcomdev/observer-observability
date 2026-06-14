package com.pipeline.observer.domain.model;

public record SystemMetricSnapshot(MemoryRecord memoryRecord, DiskRecord diskRecord, CpuRecord cpuRecord, java.time.LocalDateTime timestamp) { }
