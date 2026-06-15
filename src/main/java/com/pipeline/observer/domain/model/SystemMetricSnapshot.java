package com.pipeline.observer.domain.model;

public record SystemMetricSnapshot(RamRecord ramRecord, DiskRecord diskRecord, CpuRecord cpuRecord, java.time.LocalDateTime timestamp) { }
