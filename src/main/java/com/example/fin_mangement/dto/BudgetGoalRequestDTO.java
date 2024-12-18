package com.example.fin_mangement.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetGoalRequestDTO {
    private String name;       // 목표 이름
    private String startDate;  // 시작 날짜
    private String endDate;    // 종료 날짜
    private double amount;     // 목표 금액
}
