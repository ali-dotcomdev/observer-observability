package com.pipeline.observer.domain.model;

public record RamRecord(long totalMemoryMb, long freeMemoryMb, long usedMemoryMb) { }