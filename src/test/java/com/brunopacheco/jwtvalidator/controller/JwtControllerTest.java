package com.brunopacheco.jwtvalidator.controller;

import com.brunopacheco.jwtvalidator.fixtures.Fixtures;
import com.brunopacheco.jwtvalidator.usecase.JwtValidationUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JwtController.class)
class JwtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtValidationUseCase useCase;

    @Test
    void shouldReturnVerdadeiroWhenUseCaseReturnsVerdadeiro() throws Exception {
        when(useCase.validateJwt(anyString())).thenReturn(Fixtures.VALID_TOKEN_BOOLEAN_RESPONSE);

        mockMvc.perform(post(Fixtures.POST_API_PATH)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(Fixtures.VALID_JWT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(Fixtures.VALID_TOKEN_BOOLEAN_RESPONSE))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void shouldReturnFalsoWhenUseCaseReturnsFalso() throws Exception {
        when(useCase.validateJwt(anyString())).thenReturn(Fixtures.INVALID_TOKEN_BOOLEAN_RESPONSE);

        mockMvc.perform(post(Fixtures.POST_API_PATH)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(Fixtures.INVALID_JWT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(Fixtures.INVALID_TOKEN_BOOLEAN_RESPONSE))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void shouldReturnFalsoWhenUseCaseThrowsException() throws Exception {
        when(useCase.validateJwt(anyString())).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(post(Fixtures.POST_API_PATH)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(Fixtures.INVALID_JWT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(Fixtures.INVALID_TOKEN_BOOLEAN_RESPONSE))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }
}