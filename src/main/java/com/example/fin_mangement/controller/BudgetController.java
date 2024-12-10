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

        // 전달받은 BudgetDto 값 확인
        System.out.println("전달받은 BudgetDto: " + budgetDto);
        System.out.println("예산 이름: " + budgetDto.getBudgetName());  // 예산 이름 출력
        System.out.println("금액: " + budgetDto.getAmount());          // 금액 출력

        String username = authentication.getName(); // 현재 인증된 사용자의 이름
        System.out.println("전달받은 BudgetDTO:"+budgetDto);
        budgetService.addBudget(budgetDto, username);
        return ResponseEntity.ok("예산이 성공적으로 추가되었습니다");
    }

    // 사용자의 모든 예산 조회
    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(Authentication authentication) {
        String username = authentication.getName(); // 현재 인증된 사용자의 이름
        List<Budget> budgets = budgetService.getBudgetsByUser(username);
        return ResponseEntity.ok(budgets);
    }
}
