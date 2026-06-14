package com.pipeline.observer.domain.model;

public record MemoryRecord(long totalMemoryMb, long freeMemoryMb, long usedMemoryMb) { }