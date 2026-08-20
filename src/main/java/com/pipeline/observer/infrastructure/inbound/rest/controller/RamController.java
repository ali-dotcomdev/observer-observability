package com.pipeline.observer.infrastructure.inbound.rest.controller;

import com.pipeline.observer.domain.ports.inbound.usecase.ram.StreamRamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/metrics/ram")
@RequiredArgsConstructor
public class RamController {

    private final StreamRamUseCase streamRamUseCase;

    @GetMapping("/stream")
    public SseEmitter streamRamMetrics(){

        SseEmitter emitter = new SseEmitter(0L);

        streamRamUseCase.addEmitter(emitter);

        return emitter;
    }
}
