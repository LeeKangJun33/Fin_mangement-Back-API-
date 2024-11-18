package com.example.fin_mangement.util;

import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String secret;

    private final String testUsername = "testuser";


    @BeforeEach
    public void setUp(){
       jwtUtil = new JwtUtil();
       jwtUtil.setSecret(secret);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken("testuser");
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        String token = jwtUtil.generateToken("testuser");
        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }



    @Test
    void testTokenValidity() {
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.validateToken(token, "testuser"));
    }


    @Test
    void testTokenExpiration() {
        String token = jwtUtil.generateToken("testuser");
        assertFalse(jwtUtil.isTokenExpired(token));
    }

}