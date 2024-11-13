package com.example.fin_mangement.repository;

import com.example.fin_mangement.entity.BudgetGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetGoalRepositoy extends JpaRepository<BudgetGoal,Long> {

    List<BudgetGoal> findByUserId(Long userId);
}
