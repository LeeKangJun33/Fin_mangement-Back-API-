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

// 목표저장
  public void save(BudgetGoal budgetGoal) {
       if(budgetGoal == null) {
           throw new IllegalArgumentException("BudgetGoal 객체는 null일 수 없습니다");
       }
       budgetGoalRepositoy.save(budgetGoal);
  }

  //사용자벼 목표 조회
  public List<BudgetGoal> getGoalsByUsername(String username) {
      if (username == null || username.isEmpty()) {
          throw new IllegalArgumentException("사용자 이름은 null이거나 비어 있을 수 없습니다.");
      }
      return budgetGoalRepositoy.findByUsername(username);
  }
//목표수정
  public BudgetGoal updateBudgetGoal(Long id,BudgetGoal updateGoal) {
      if (id == null || updateGoal == null) {
          throw new IllegalArgumentException("id와 updateGoal은 null일 수 없습니다.");
      }

      BudgetGoal existingGoal = budgetGoalRepositoy.findById(id)
              .orElseThrow(() -> new RuntimeException("ID " + id + "에 해당하는 목표를 찾을 수 없습니다."));

      existingGoal.setTargetAmount(updateGoal.getTargetAmount());
      existingGoal.setStartDate(updateGoal.getStartDate());
      existingGoal.setEndDate(updateGoal.getEndDate());
      existingGoal.setDescription(updateGoal.getDescription());

      return budgetGoalRepositoy.save(existingGoal);
  }

  public void deleteBudgetGoal(Long id) {
      if (id == null) {
          throw new IllegalArgumentException("삭제하려는 목표의 ID는 null일 수 없습니다.");
      }

      if (!budgetGoalRepositoy.existsById(id)) {
          throw new RuntimeException("ID " + id + "에 해당하는 목표를 찾을 수 없습니다.");
      }

      budgetGoalRepositoy.deleteById(id);
  }

}
