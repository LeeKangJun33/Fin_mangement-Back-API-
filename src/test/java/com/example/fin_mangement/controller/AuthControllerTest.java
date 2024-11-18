package com.example.fin_mangement.controller;

import com.example.fin_mangement.dto.AuthRequest;
import com.example.fin_mangement.dto.AuthResponse;
import com.example.fin_mangement.service.CustomUserDetailsService;
import com.example.fin_mangement.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private String testUsername = "testuser";
    private String testPassword = "testpassword";
    private String jwtToken = "dummyJwtToken";

    @BeforeEach
    void setUp() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(testUsername);
        when(jwtUtil.generateToken(testUsername)).thenReturn(jwtToken);
    }

    @Test
    void testCreateAuthenticationToken() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(testUsername);
        authRequest.setPassword(testPassword);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(testUsername, testPassword)))
                .thenReturn(new UsernamePasswordAuthenticationToken(testUsername, testPassword));

        when(userDetailsService.loadUserByUsername(testUsername)).thenReturn(mock(UserDetails.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + testUsername + "\", \"password\": \"" + testPassword + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value(jwtToken));
    }
}
