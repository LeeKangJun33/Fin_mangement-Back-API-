package com.example.fin_mangement.IntegrationTest;

import com.example.fin_mangement.entity.Transaction;
import com.example.fin_mangement.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    public void setup() {
        transactionRepository.deleteAll(); // 테스트 전 데이터 초기화
        // @Builder를 이용한 Transaction 생성
        transactionRepository.save(
                Transaction.builder()
                        .username("testUser")
                        .description("Coffee")
                        .amount(5.0)
                        .date(LocalDateTime.now())
                        .category("Food")
                        .build()
        );

        transactionRepository.save(
                Transaction.builder()
                        .username("testUser")
                        .description("Groceries")
                        .amount(50.0)
                        .date(LocalDateTime.now())
                        .category("Shopping")
                        .build()
        );
    }

    @Test
    @DisplayName("사용자 거래 목록 조회 테스트")
    @WithMockUser(username = "testUser", roles = "USER")  // Mock 인증 정보 설정
    void testGetTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"));
    }

    @Test
    @DisplayName("거래 추가 테스트")
    public void testAddTransaction() throws Exception {
        String newTransaction = """
        {
            "username": "testUser",
            "description": "Book",
            "amount": 15.0,
            "date": "2024-11-20T10:00:00",
            "category": "Education"
        }
        """;

        mockMvc.perform(post("/api/transactions")
                        .header("Authorization", "Bearer YOUR_JWT_TOKEN_HERE") // 인증 헤더 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTransaction))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("거래가 성공적으로 추가되었습니다!")));

        // 추가된 데이터 검증
        mockMvc.perform(get("/api/transactions")
                        .header("Authorization", "Bearer YOUR_JWT_TOKEN_HERE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].description", is("Book")));
    }
}
