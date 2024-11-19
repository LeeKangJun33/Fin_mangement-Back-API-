package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.Transaction;
import com.example.fin_mangement.repository.TransactionRepository;
import com.example.fin_mangement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    // 거래 추가
    @PostMapping
    public ResponseEntity<String> addTransaction(@RequestBody Map<String, Object> request, Principal principal) {
        // Principal에서 username 가져오기
        String username = principal.getName();

        // 요청 데이터 추출 및 검증
        String description = (String) request.get("description");
        double amount = Double.parseDouble(request.get("amount").toString());
        String category = (String) request.get("category");
        LocalDateTime date = LocalDateTime.parse((String) request.get("date"));

        // 서비스 호출
        transactionService.addTransaction(username, description, amount, date, category);
        return ResponseEntity.ok("거래가 성공적으로 추가되었습니다!");
    }

    // 사용자별 거래 조회
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Transaction> getUserTransactions(Principal principal) {
        // Principal로부터 userId를 가져옴
        String username = principal.getName();
        return transactionRepository.findByUsername(username);
    }


    // 모든 거래 조회 (관리자 전용)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Transaction> getAllTransactionsForAdmin() {
        return transactionRepository.findAll();
    }

}

