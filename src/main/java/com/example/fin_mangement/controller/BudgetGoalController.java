package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.BudgetGoal;
import com.example.fin_mangement.service.BudgetGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/budget-goals")
@RequiredArgsConstructor
public class BudgetGoalController {

    private final BudgetGoalService budgetGoalService;

    //목표설정
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BudgetGoal> addBudgetGoal(@RequestBody BudgetGoal budgetGoal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName(); // 인증된 사용자의 이름 가져오기
        budgetGoal.setUsername(username);
        return ResponseEntity.ok(budgetGoalService.addBudgetGoal(budgetGoal));
    }

    //사용자별 목표 조회
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BudgetGoal>> getBudgetGoals(Principal principal){
        if(principal == null || principal.getName() ==null){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String username = principal.getName();
        List<BudgetGoal> goals = budgetGoalService.getGoalsByUsername(username);
        return ResponseEntity.ok(goals);
    }

    //목표수정
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BudgetGoal> updateBudgetGoal(@PathVariable Long id,@RequestBody BudgetGoal updatedGoal){
        return ResponseEntity.ok(budgetGoalService.updateBudgetGoal(id,updatedGoal));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteBudgetGoal(@PathVariable Long id){
        budgetGoalService.deleteBudgetGoal(id);
        return ResponseEntity.ok("목표가 성공적으로 삭제되었습니다.");
    }
}
