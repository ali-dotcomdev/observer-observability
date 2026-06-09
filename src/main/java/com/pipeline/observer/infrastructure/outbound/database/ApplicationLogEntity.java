package com.pipeline.observer.infrastructure.outbound.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_logs")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logLevel;
    private String message;
    private LocalDateTime timestamp;
}
