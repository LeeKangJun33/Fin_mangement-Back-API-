package com.example.fin_mangement.service;

import com.example.fin_mangement.entity.Transaction;
import com.example.fin_mangement.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    // 거래 추가
    public Transaction addTransaction(String username, String description, double amount, LocalDateTime date, String category) {
        Transaction transaction = Transaction.builder()
                .username(username)
                .amount(amount)
                .category(category)
                .date(date)
                .description(description)
                .build();
        return transactionRepository.save(transaction);
    }

    // 사용자별 거래 조회
    public List<Transaction> getTransactionsByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return transactionRepository.findByUsername(username);
    }

    // 입력 데이터 검증
    private void validateTransactionInput(String username, String description, double amount, String category) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
    }
}
