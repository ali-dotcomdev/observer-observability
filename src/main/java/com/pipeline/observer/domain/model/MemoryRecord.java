package com.pipeline.observer.domain.model;

public record MemoryRecord(int processors, long totalMemoryMb, long freeMemoryMb, long usedMemoryMb) { }