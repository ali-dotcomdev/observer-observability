package com.pipeline.observer.infrastructure.outbound.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="system_disk_metrics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemDiskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long totalSpaceGb;
    private long usedSpaceGb;
    private long freeSpaceGb;
    private LocalDateTime timestamp;
}
