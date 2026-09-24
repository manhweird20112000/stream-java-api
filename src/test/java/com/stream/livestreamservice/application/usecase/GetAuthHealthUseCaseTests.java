package com.stream.livestreamservice.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.stream.livestreamservice.application.dto.AuthHealthResponse;

class GetAuthHealthUseCaseTests {

    @Test
    void executeReturnsAuthServiceHealth() {
        GetAuthHealthUseCase useCase = new GetAuthHealthUseCase();

        AuthHealthResponse response = useCase.execute();

        assertThat(response.service()).isEqualTo("livestream-service");
        assertThat(response.status()).isEqualTo("UP");
    }
}
