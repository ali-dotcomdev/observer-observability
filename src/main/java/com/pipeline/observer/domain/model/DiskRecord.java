package com.pipeline.observer.domain.model;

public record DiskRecord(long totalSpaceGb, long freeSpaceGb, long usedSpaceGb) { }
