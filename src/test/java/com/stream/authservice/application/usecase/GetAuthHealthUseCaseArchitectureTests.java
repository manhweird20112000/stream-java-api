package com.stream.authservice.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

class GetAuthHealthUseCaseArchitectureTests {

    @Test
    void useCaseDoesNotDependOnSpringStereotype() {
        assertThat(GetAuthHealthUseCase.class.isAnnotationPresent(Service.class)).isFalse();
    }
}
