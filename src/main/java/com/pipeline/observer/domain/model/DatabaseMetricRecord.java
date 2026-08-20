package com.pipeline.observer.domain.model;

public record DatabaseMetricRecord(int activeConnections, long databaseSizeBytes) { }
