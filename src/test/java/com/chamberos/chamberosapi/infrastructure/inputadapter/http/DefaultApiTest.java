package com.chamberos.chamberosapi.infrastructure.inputadapter.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DefaultAPI.class)
public class DefaultApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRenderTheRootAppContent() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.is("Chamberos API")));
    }
}
