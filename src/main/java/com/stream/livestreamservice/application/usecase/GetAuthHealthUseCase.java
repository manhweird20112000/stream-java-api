package com.stream.livestreamservice.application.usecase;

import com.stream.livestreamservice.application.dto.AuthHealthResponse;
import com.stream.livestreamservice.domain.model.ServiceHealth;

public class GetAuthHealthUseCase {

    public AuthHealthResponse execute() {
        ServiceHealth health = ServiceHealth.up("livestream-service");
        return new AuthHealthResponse(health.service(), health.status());
    }
}
