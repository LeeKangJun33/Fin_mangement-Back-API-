package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.Budget;
import com.example.fin_mangement.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    //예산설정(인증필요)
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String setBudget(@RequestParam double amount, Principal principal) {
        Budget budget = new Budget();
        budget.setAmount(amount);
        budget.setUserId(principal.getName()); //로그인한 사용자 Id
        budgetRepository.save(budget);
        return "예산이 설정되었습니다." + amount;
    }

    //예산조회
    // findTopByOrderByCreatedAtDesc 는 여러 설정 내역이 있을경ㅇ 가장 최근에 설정한 예산을 가져오기위한 메서드
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public double getBudget() {
       return budgetRepository.findTopByOrderByCreatedAtDesc()
               .map(Budget::getAmount)
               .orElse(0.0); //예산이 없을경우 기본값 0 으로 설정
    }
}
