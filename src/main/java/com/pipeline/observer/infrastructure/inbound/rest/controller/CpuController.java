package com.pipeline.observer.infrastructure.inbound.rest.controller;

import com.pipeline.observer.domain.ports.inbound.usecase.cpu.StreamCpuUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/metrics/cpu")
@RequiredArgsConstructor
public class CpuController {

    private final StreamCpuUseCase streamCpuUseCase;

    @GetMapping("/stream")
    public SseEmitter streamCpuMetrics(){

        SseEmitter emitter = new SseEmitter(0L);

        streamCpuUseCase.addEmitter(emitter);

        return emitter;
    }
}
