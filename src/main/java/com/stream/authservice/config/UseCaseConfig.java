package com.stream.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stream.authservice.application.usecase.GetAuthHealthUseCase;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetAuthHealthUseCase getAuthHealthUseCase() {
        return new GetAuthHealthUseCase();
    }
}
