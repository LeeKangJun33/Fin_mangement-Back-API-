package com.example.fin_mangement.controller;

import com.example.fin_mangement.dto.BudgetGoalRequestDTO;
import com.example.fin_mangement.entity.BudgetGoal;
import com.example.fin_mangement.service.BudgetGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/budget-goals")
@RequiredArgsConstructor
public class BudgetGoalController {

    private final BudgetGoalService budgetGoalService;

    // 목표 설정
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addBudgetGoal(@RequestBody BudgetGoalRequestDTO request, Principal principal) {
        String username = principal.getName();

        // 입력값 검증
        if (request.getName() == null || request.getStartDate() == null ||
                request.getEndDate() == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("모든 필드를 올바르게 입력하세요.");
        }

        // 날짜 변환 및 유효성 검사
        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());
        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body("시작 날짜는 종료 날짜 이전이어야 합니다.");
        }

        // 엔티티 변환 및 저장
        BudgetGoal goal = BudgetGoal.builder()
                .username(username)
                .targetAmount(request.getAmount()) // BigDecimal 그대로 사용
                .startDate(startDate)
                .endDate(endDate)
                .description(request.getName())
                .build();

        budgetGoalService.save(goal);

        return ResponseEntity.ok("목표 예산이 성공적으로 추가되었습니다!");
    }

    // 사용자별 목표 조회
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BudgetGoal>> getBudgetGoals(Principal principal) {
        String username = principal.getName();
        List<BudgetGoal> goals = budgetGoalService.getGoalsByUsername(username);
        return ResponseEntity.ok(goals);
    }

    // 목표 수정
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateBudgetGoal(@PathVariable Long id, @RequestBody BudgetGoalRequestDTO request) {
        // 날짜 변환 및 검증
        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());
        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body("시작 날짜는 종료 날짜 이전이어야 합니다.");
        }

        // 엔티티 변환 및 업데이트
        BudgetGoal updatedGoal = BudgetGoal.builder()
                .targetAmount(request.getAmount()) // BigDecimal 그대로 사용
                .startDate(startDate)
                .endDate(endDate)
                .description(request.getName())
                .build();

        BudgetGoal result = budgetGoalService.updateBudgetGoal(id, updatedGoal);
        return ResponseEntity.ok(result);
    }

    // 목표 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteBudgetGoal(@PathVariable Long id) {
        budgetGoalService.deleteBudgetGoal(id);
        return ResponseEntity.ok("목표가 성공적으로 삭제되었습니다.");
    }
}
