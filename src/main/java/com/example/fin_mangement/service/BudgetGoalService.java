package com.example.fin_mangement.service;

import com.example.fin_mangement.entity.BudgetGoal;
import com.example.fin_mangement.repository.BudgetGoalRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetGoalService {

    private final BudgetGoalRepositoy budgetGoalRepositoy;

    public BudgetGoal createGoal(BudgetGoal goal) {
        return budgetGoalRepositoy.save(goal);
    }

    public List<BudgetGoal> getGoalByUserId(long userId) {
        return budgetGoalRepositoy.findByUserId(userId);
    }

    public BudgetGoal updateGoal(Long goalId,BudgetGoal updateGoal){
        BudgetGoal existingGoal = budgetGoalRepositoy.findById(goalId).orElseThrow();
        existingGoal.setTargetAmount(updateGoal.getTargetAmount());
        existingGoal.setCategory(updateGoal.getCategory());
        existingGoal.setPeriod(updateGoal.getPeriod());
        return budgetGoalRepositoy.save(existingGoal);
    }

    public BigDecimal calculateAchievementRate(BigDecimal totalSpent, BigDecimal targetAmount) {
        if (targetAmount.compareTo(BigDecimal.ZERO) > 0) {
            return totalSpent.divide(targetAmount, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        } else {
            return BigDecimal.ZERO;
        }
    }

}
