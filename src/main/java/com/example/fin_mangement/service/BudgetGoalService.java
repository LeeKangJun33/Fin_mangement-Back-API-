package com.example.fin_mangement.service;

import com.example.fin_mangement.entity.BudgetGoal;
import com.example.fin_mangement.repository.BudgetGoalRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetGoalService {

    private final BudgetGoalRepositoy budgetGoalRepositoy;

    // 목표 저장
    public BudgetGoal save(BudgetGoal budgetGoal) {
        if (budgetGoal == null) {
            throw new IllegalArgumentException("BudgetGoal 객체는 null일 수 없습니다.");
        }
        validateBudgetGoalDates(budgetGoal.getStartDate(), budgetGoal.getEndDate());
        validateTargetAmount(budgetGoal.getTargetAmount());
        return budgetGoalRepositoy.save(budgetGoal);
    }

    // 사용자별 목표 조회
    public List<BudgetGoal> getGoalsByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("사용자 이름은 null이거나 비어 있을 수 없습니다.");
        }
        return budgetGoalRepositoy.findByUsername(username);
    }

    // 목표 수정
    public BudgetGoal updateBudgetGoal(Long id, BudgetGoal updatedGoal) {
        if (id == null || updatedGoal == null) {
            throw new IllegalArgumentException("id와 updatedGoal은 null일 수 없습니다.");
        }

        BudgetGoal existingGoal = budgetGoalRepositoy.findById(id)
                .orElseThrow(() -> new RuntimeException("ID " + id + "에 해당하는 목표를 찾을 수 없습니다."));

        validateBudgetGoalDates(updatedGoal.getStartDate(), updatedGoal.getEndDate());
        validateTargetAmount(updatedGoal.getTargetAmount());

        existingGoal.setTargetAmount(updatedGoal.getTargetAmount());
        existingGoal.setStartDate(updatedGoal.getStartDate());
        existingGoal.setEndDate(updatedGoal.getEndDate());
        existingGoal.setDescription(updatedGoal.getDescription());

        return budgetGoalRepositoy.save(existingGoal);
    }

    // 목표 삭제
    public void deleteBudgetGoal(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("삭제하려는 목표의 ID는 null일 수 없습니다.");
        }

        if (!budgetGoalRepositoy.existsById(id)) {
            throw new RuntimeException("ID " + id + "에 해당하는 목표를 찾을 수 없습니다.");
        }

        budgetGoalRepositoy.deleteById(id);
    }

    // 목표 금액 검증
    private void validateTargetAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("목표 금액은 null이거나 0 이하일 수 없습니다.");
        }
    }

    // 목표 날짜 검증
    private void validateBudgetGoalDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작 날짜와 종료 날짜는 null일 수 없습니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜는 종료 날짜보다 늦을 수 없습니다.");
        }
    }
}
