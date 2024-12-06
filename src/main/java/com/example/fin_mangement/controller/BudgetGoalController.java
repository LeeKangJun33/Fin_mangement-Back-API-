package com.example.fin_mangement.controller;

import com.example.fin_mangement.dto.BudgetGoalRequestDTO;
import com.example.fin_mangement.entity.Budget;
import com.example.fin_mangement.entity.BudgetGoal;
import com.example.fin_mangement.service.BudgetGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budget-goals")
@RequiredArgsConstructor
public class BudgetGoalController {

    private final BudgetGoalService budgetGoalService;

    //목표설정
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addBudgetGoal(@RequestBody BudgetGoalRequestDTO request, Principal principal){
     String username = principal.getName();

     if(request.getName()==null || request.getStartDate() ==null || request.getEndDate()==null || request.getAmount() <=0){
         return ResponseEntity.badRequest().body("모든 필드를 올바르게 입력하세요.");
     }

     LocalDate startDate = LocalDate.parse(request.getStartDate());
     LocalDate endDate = LocalDate.parse(request.getEndDate());

     if(startDate.isAfter(endDate)){
         return ResponseEntity.badRequest().body("시작 날짜는 종료 날짜 이전이어야 합니다.");
     }

     BudgetGoal goal = new BudgetGoal();
     goal.setDescription(request.getName());
     goal.setTargetAmount(request.getAmount());
     goal.setStartDate(startDate);
     goal.setEndDate(endDate);
     goal.setUsername(username);

     budgetGoalService.save(goal);

     return ResponseEntity.ok("목표예산 추가 성공!");

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
    public ResponseEntity<BudgetGoal> updateBudgetGoal(@PathVariable Long id, @RequestBody BudgetGoalRequestDTO request) {
        // BudgetGoalRequestDTO를 BudgetGoal로 변환
        BudgetGoal updatedGoal = new BudgetGoal();
        updatedGoal.setDescription(request.getName());
        updatedGoal.setTargetAmount(request.getAmount());
        updatedGoal.setStartDate(LocalDate.parse(request.getStartDate()));
        updatedGoal.setEndDate(LocalDate.parse(request.getEndDate()));

        BudgetGoal result = budgetGoalService.updateBudgetGoal(id, updatedGoal);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteBudgetGoal(@PathVariable Long id){
        budgetGoalService.deleteBudgetGoal(id);
        return ResponseEntity.ok("목표가 성공적으로 삭제되었습니다.");
    }
}
