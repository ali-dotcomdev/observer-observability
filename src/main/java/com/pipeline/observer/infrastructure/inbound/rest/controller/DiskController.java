package com.pipeline.observer.infrastructure.inbound.rest.controller;

import com.pipeline.observer.domain.ports.inbound.usecase.disk.StreamDiskMetricUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/metrics/disk")
@RequiredArgsConstructor
public class DiskController {

    private final StreamDiskMetricUseCase streamDisk;

    @GetMapping("/stream")
    public SseEmitter streamDiskMetrics(){

        SseEmitter emitter = new SseEmitter(0L);

        streamDisk.addEmitter(emitter);
        return emitter;
    }
}
