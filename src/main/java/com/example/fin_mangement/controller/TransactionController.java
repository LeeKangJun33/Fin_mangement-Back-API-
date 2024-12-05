package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.Transaction;
import com.example.fin_mangement.repository.TransactionRepository;
import com.example.fin_mangement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        try {
            // 요청 데이터 추출 및 검증
            String description = (String) request.get("description");
            double amount = Double.parseDouble(request.get("amount").toString());
            String category = (String) request.get("category");

            // 날짜 처리
            String dateString = (String) request.get("date");
            LocalDateTime date;

            // 날짜 형식 유연성 추가
            if (dateString.length() == 10) { // 날짜만 제공된 경우
                date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            } else { // 날짜와 시간이 함께 제공된 경우
                date = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            // 서비스 호출
            transactionService.addTransaction(username, description, amount, date, category);
            return ResponseEntity.ok("거래가 성공적으로 추가되었습니다!");

        } catch (DateTimeParseException e) {
            // 날짜 형식 오류 처리
            return ResponseEntity.badRequest().body("잘못된 날짜 형식입니다. yyyy-MM-dd 또는 yyyy-MM-ddTHH:mm:ss 형식을 사용하세요.");
        } catch (Exception e) {
            // 기타 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("거래 추가 중 오류가 발생했습니다.");
        }
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

