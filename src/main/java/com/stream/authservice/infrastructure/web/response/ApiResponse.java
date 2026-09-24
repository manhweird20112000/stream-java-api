package com.stream.authservice.infrastructure.web.response;

import java.time.Instant;

public record ApiResponse<T>(
        boolean success,
        String code,
        String message,
        T data,
        Instant timestamp,
        String path
) {

    public static <T> ApiResponse<T> success(T data, String path) {
        return new ApiResponse<>(true, "SUCCESS", "Success", data, Instant.now(), path);
    }
}
