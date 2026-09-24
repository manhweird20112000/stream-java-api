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
import com.stream.authservice.infrastructure.web.response.ApiResponseBodyAdvice;

@WebMvcTest(AuthHealthController.class)
@Import({UseCaseConfig.class, ApiResponseBodyAdvice.class})
class AuthHealthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthReturnsServiceStatus() throws Exception {
        mockMvc.perform(get("/api/auth/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.code", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.data.service", is("auth-service")))
                .andExpect(jsonPath("$.data.status", is("UP")))
                .andExpect(jsonPath("$.path", is("/api/auth/health")))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
