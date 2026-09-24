package com.stream.authservice.application.usecase;

import com.stream.authservice.application.dto.AuthHealthResponse;
import com.stream.authservice.domain.model.ServiceHealth;

public class GetAuthHealthUseCase {

    public AuthHealthResponse execute() {
        ServiceHealth health = ServiceHealth.up("auth-service");
        return new AuthHealthResponse(health.service(), health.status());
    }
}
