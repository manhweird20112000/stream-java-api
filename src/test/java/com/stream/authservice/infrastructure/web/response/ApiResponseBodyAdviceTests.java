package com.stream.authservice.infrastructure.web.response;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class ApiResponseBodyAdviceTests {

    @Test
    void doesNotWrapApiResponseAgain() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new AlreadyWrappedController())
                .setControllerAdvice(new ApiResponseBodyAdvice())
                .build();

        mockMvc.perform(get("/wrapped"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.code", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.data", is("ok")))
                .andExpect(jsonPath("$.path", is("/wrapped")));
    }

    @RestController
    public static class AlreadyWrappedController {

        @GetMapping("/wrapped")
        ApiResponse<String> wrapped() {
            return ApiResponse.success("ok", "/wrapped");
        }
    }
}
