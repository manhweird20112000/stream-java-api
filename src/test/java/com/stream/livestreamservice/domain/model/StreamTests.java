package com.stream.livestreamservice.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class StreamTests {

    @Test
    void createBuildsNewStreamWithCreatedStatus() {
        UUID id = UUID.randomUUID();
        Instant beforeCreate = Instant.now();

        Stream stream = Stream.create(
                id,
                "Morning show",
                "Daily livestream",
                "stream-key-1",
                "https://example.com/play/stream-key-1");

        assertThat(stream.id()).isEqualTo(id);
        assertThat(stream.title()).isEqualTo("Morning show");
        assertThat(stream.description()).isEqualTo("Daily livestream");
        assertThat(stream.status()).isEqualTo(StreamStatus.CREATED);
        assertThat(stream.streamKey()).isEqualTo("stream-key-1");
        assertThat(stream.playbackUrl()).isEqualTo("https://example.com/play/stream-key-1");
        assertThat(stream.createdAt()).isAfterOrEqualTo(beforeCreate);
        assertThat(stream.updatedAt()).isEqualTo(stream.createdAt());
    }
}
