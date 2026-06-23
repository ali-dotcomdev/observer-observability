package com.pipeline.observer.infrastructure.outbound.database.adapter;

import com.pipeline.observer.domain.ports.outbound.database.DatabaseMonitorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostgresMonitorAdapter implements DatabaseMonitorPort {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int fetchActiveConnections() {

        int activeConnections = jdbcTemplate.queryForObject("SELECT count(*) FROM pg_stat_activity " +
                "WHERE state = 'active'", Integer.class);
        return activeConnections;
    }

    @Override
    public long fetchDatabaseSizeBytes() {
        long databaseSizeBytes = jdbcTemplate.queryForObject("SELECT pg_database_size(current_database())", Long.class);
        return databaseSizeBytes;
    }
}
