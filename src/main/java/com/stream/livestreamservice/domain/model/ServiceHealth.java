package com.stream.livestreamservice.domain.model;

public record ServiceHealth(String service, String status) {

    public static ServiceHealth up(String service) {
        return new ServiceHealth(service, "UP");
    }
}
