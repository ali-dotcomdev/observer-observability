package com.pipeline.observer.infrastructure.log;

import com.pipeline.observer.domain.ports.outbound.MetricPorts;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

@Component
public class NLogger implements MetricPorts {

    private final Path LOG_FILE = Paths.get("logs/metrics.txt");

    @Override
    public void saveMetrics(int processors, long freeMemoryMb, long totalMemoryMb){
        String logEntry = String.format("date: %s, processors: %s, free memory: %s, total memory: %s \n"
                ,LocalDateTime.now(), processors, freeMemoryMb, totalMemoryMb);

        try{

            Files.write(
                LOG_FILE,
                logEntry.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );

        } catch (IOException e){
            System.err.println("failed to write log: " + e.getMessage());
        }
    }
}
