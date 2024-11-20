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

  public BudgetGoal addBudgetGoal(BudgetGoal budgetGoal) {
      return budgetGoalRepositoy.save(budgetGoal);
  }

  public List<BudgetGoal> getGoalsByUsername(String username) {
      return budgetGoalRepositoy.findByUsername(username);
  }

  public BudgetGoal updateBudgetGoal(Long id,BudgetGoal updateGoal) {
      BudgetGoal goal = budgetGoalRepositoy.findById(id)
              .orElseThrow(() -> new RuntimeException("목표를 찾을수없습니다."));
      goal.setTargetAmount(updateGoal.getTargetAmount());
      goal.setStartDate(updateGoal.getStartDate());
      goal.setEndDate(updateGoal.getEndDate());
      goal.setDescription(updateGoal.getDescription());
      return budgetGoalRepositoy.save(goal);
  }

  public void deleteBudgetGoal(Long id) {
      budgetGoalRepositoy.deleteById(id);
  }



}
