package com.pipeline.observer.infrastructure.outbound.database.repository;

import com.pipeline.observer.infrastructure.outbound.database.ApplicationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationLogRepository extends JpaRepository<ApplicationLogEntity, Long> {

}
