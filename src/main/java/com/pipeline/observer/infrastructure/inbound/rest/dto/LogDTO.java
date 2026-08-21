package com.pipeline.observer.infrastructure.inbound.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LogDTO {

    private final String logLevel;
    private final String message;
    private final LocalDateTime timestamp;
}
