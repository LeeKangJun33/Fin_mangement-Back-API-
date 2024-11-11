package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.Transaction;
import com.example.fin_mangement.repository.TransactionRepository;
import com.example.fin_mangement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {


    private TransactionRepository transactionRepository;

    // 월별 지출 요약
    @GetMapping("/monthly-expense")
    @PreAuthorize("isAuthenticated()")
    public Map<String, Object> getMonthlyExpense(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        Map<String, Double> monthlyExpenses = new HashMap<>();
        for (Transaction transaction : transactions) {
            String month = transaction.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            monthlyExpenses.put(month, monthlyExpenses.getOrDefault(month, 0.0) + transaction.getAmount());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", new ArrayList<>(monthlyExpenses.keySet()));
        response.put("values", new ArrayList<>(monthlyExpenses.values()));
        return response;
    }
    // 카테고리별 지출 분포
    @GetMapping("/category-expense")
    @PreAuthorize("isAuthenticated()")
    public Map<String, Object> getCategoryExpense(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        Map<String, Double> categoryExpenses = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)));

        Map<String, Object> response = new HashMap<>();
        response.put("labels", new ArrayList<>(categoryExpenses.keySet()));
        response.put("values", new ArrayList<>(categoryExpenses.values()));
        return response;
    }


    // 거래 추가
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addTransaction(@RequestBody Transaction transaction, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        transaction.setUserId(userId);
        transactionRepository.save(transaction);
        return "거래가 추가되었습니다!";
    }

    // 모든 거래 조회
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Transaction> getAllTransactions(Principal principal) {
        if(principal == null){
            throw new RuntimeException("User is not authenticated");
        }
        Long userId = Long.parseLong(principal.getName());
        return transactionRepository.findByUserId(userId);
    }

}
