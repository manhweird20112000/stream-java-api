package com.stream.livestreamservice.infrastructure.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stream.livestreamservice.application.dto.AuthHealthResponse;
import com.stream.livestreamservice.application.usecase.GetAuthHealthUseCase;

@RestController
@RequestMapping("/api/auth")
public class AuthHealthController {

    private final GetAuthHealthUseCase getAuthHealthUseCase;

    public AuthHealthController(GetAuthHealthUseCase getAuthHealthUseCase) {
        this.getAuthHealthUseCase = getAuthHealthUseCase;
    }

    @GetMapping("/health")
    public AuthHealthResponse health() {
        return getAuthHealthUseCase.execute();
    }
}
