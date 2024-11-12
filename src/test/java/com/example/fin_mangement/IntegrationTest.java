package com.example.fin_mangement;

import com.example.fin_mangement.entity.User;
import com.example.fin_mangement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private String authToken;

    @BeforeEach
    public void setUp() throws Exception {

        User user = User.builder()
                .username("testuser")
                .password("testpassword")
                .email("test@test.com")
                .build();
        userRepository.save(user);

        authToken = "발급된 JWT 토큰";
    }
}
