package com.example.fin_mangement.repository;

import com.example.fin_mangement.entity.BudgetGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetGoalRepositoy extends JpaRepository<BudgetGoal,Long> {

    List<BudgetGoal> findByUsername(String username);
}
