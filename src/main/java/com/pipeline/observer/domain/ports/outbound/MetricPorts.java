package com.pipeline.observer.domain.ports.outbound;

public interface MetricPorts {
    void saveMetrics(int processors, long freeMemoryMb, long totalMemoryMb);
}
