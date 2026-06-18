package com.pipeline.observer.infrastructure.outbound.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "database_metrics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseMetricsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int activeConnections;
    private long databaseSizeBytes;
    private LocalDateTime timestamp;
}
