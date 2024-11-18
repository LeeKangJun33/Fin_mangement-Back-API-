package com.example.fin_mangement.controller;

import com.example.fin_mangement.dto.BudgetDto;
import com.example.fin_mangement.entity.Budget;
import com.example.fin_mangement.repository.BudgetRepository;
import com.example.fin_mangement.service.BudgetService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Data
@RequestMapping("/api/budget")
public class BudgetController {

   private final BudgetService budgetService;


    // 예산 추가
    @PostMapping
    public ResponseEntity<String> addBudget(@RequestBody BudgetDto budgetDto, Authentication authentication) {
        String username = authentication.getName(); // 현재 인증된 사용자의 이름
        budgetService.addBudget(budgetDto, username);
        return ResponseEntity.ok("Budget added successfully!");
    }

    // 사용자의 모든 예산 조회
    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(Authentication authentication) {
        String username = authentication.getName(); // 현재 인증된 사용자의 이름
        List<Budget> budgets = budgetService.getBudgetsByUser(username);
        return ResponseEntity.ok(budgets);
    }
}
