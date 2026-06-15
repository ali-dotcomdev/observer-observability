package com.pipeline.observer.infrastructure.outbound.database.entity;

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
public class SystemMemoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long freeMemoryMb;
    private long totalMemoryMb;
    @Column(name = "used_memory_mb")
    private long usedMemoryMb;
    private LocalDateTime timestamp;
}
