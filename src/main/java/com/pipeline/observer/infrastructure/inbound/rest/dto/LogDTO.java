package com.pipeline.observer.infrastructure.inbound.rest.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class LogDTO {

    public String logLevel;
    public String message;
    public LocalDateTime timestamp;
}
