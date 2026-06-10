package com.pipeline.observer.infrastructure.outbound.database;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_metrics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemMetricEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int processors;
    private long freeMemoryMb;
    private long totalMemoryMb;
    @Column(name = "used_memory_mb")
    private long usedMemoryMb;
    private LocalDateTime timestamp;
}
