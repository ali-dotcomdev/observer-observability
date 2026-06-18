package com.pipeline.observer.domain.model;

public record DatabaseMetricRecord(int activeConnection, long dataBaseSizeBytes) { }
