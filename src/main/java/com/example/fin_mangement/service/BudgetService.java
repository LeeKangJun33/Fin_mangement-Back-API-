package com.example.fin_mangement.service;

import com.example.fin_mangement.dto.BudgetDto;
import com.example.fin_mangement.entity.Budget;
import com.example.fin_mangement.repository.BudgetRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public void addBudget(BudgetDto budgetDto,String username) {
        Budget budget = new Budget();
        budget.setAmount(budgetDto.getAmount());
        budget.setUserId(username);
        budgetRepository.save(budget);
    }

    //사용자의 모든 예산 조회
    public List<Budget> getBudgetsByUser(String username) {
        return budgetRepository.findByUserId(username);
    }

}
