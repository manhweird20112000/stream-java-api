package com.stream.livestreamservice.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class StreamMigrationTests {

    @Test
    void firstMigrationCreatesStreamTable() throws Exception {
        ClassPathResource migration = new ClassPathResource("db/migration/V1__create_stream_table.sql");

        assertThat(migration.exists()).isTrue();

        String sql = migration.getContentAsString(StandardCharsets.UTF_8);
        assertThat(sql).contains("CREATE TABLE IF NOT EXISTS stream");
        assertThat(sql).contains("id UUID PRIMARY KEY");
        assertThat(sql).contains("stream_key VARCHAR(255) NOT NULL UNIQUE");
        assertThat(sql).contains("created_at TIMESTAMPTZ NOT NULL DEFAULT now()");
        assertThat(sql).contains("updated_at TIMESTAMPTZ NOT NULL DEFAULT now()");
    }
}
