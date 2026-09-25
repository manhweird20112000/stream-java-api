package com.stream.livestreamservice.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Stream(
        UUID id,
        String title,
        String description,
        StreamStatus status,
        String streamKey,
        String playbackUrl,
        Instant createdAt,
        Instant updatedAt) {

    public static Stream create(
            UUID id,
            String title,
            String description,
            String streamKey,
            String playbackUrl) {
        Instant now = Instant.now();
        return new Stream(
                id,
                title,
                description,
                StreamStatus.CREATED,
                streamKey,
                playbackUrl,
                now,
                now);
    }
}
