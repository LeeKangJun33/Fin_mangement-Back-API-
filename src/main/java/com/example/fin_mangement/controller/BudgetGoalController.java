package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.BudgetGoal;
import com.example.fin_mangement.service.BudgetGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-goals")
@RequiredArgsConstructor
public class BudgetGoalController {

    private final BudgetGoalService budgetGoalService;

    @PostMapping
    public BudgetGoal createBudgetGoal(@RequestBody BudgetGoal budgetGoal) {
        return budgetGoalService.createGoal(budgetGoal);
    }

    @GetMapping("/{userId}")
    public List<BudgetGoal> getBudgetGoals(@PathVariable Long userId) {
        return budgetGoalService.getGoalByUserId(userId);
    }

    @PutMapping("{goalId}")
    public BudgetGoal updateBudgetGoal(@PathVariable Long userId, @RequestBody BudgetGoal updatedGoal) {
        return budgetGoalService.updateGoal(userId, updatedGoal);
    }
}
