package com.stream.livestreamservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stream.livestreamservice.application.usecase.GetAuthHealthUseCase;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetAuthHealthUseCase getAuthHealthUseCase() {
        return new GetAuthHealthUseCase();
    }
}
