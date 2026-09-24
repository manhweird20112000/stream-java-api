package com.stream.authservice.infrastructure.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.stream.authservice.config.UseCaseConfig;

@WebMvcTest(AuthHealthController.class)
@Import(UseCaseConfig.class)
class AuthHealthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthReturnsServiceStatus() throws Exception {
        mockMvc.perform(get("/api/auth/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("auth-service")))
                .andExpect(jsonPath("$.status", is("UP")));
    }
}
