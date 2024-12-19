package com.example.fin_mangement.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetGoalRequestDTO {
    private String name;       // 목표 이름

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;  // 시작 날짜

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;    // 종료 날짜

    private BigDecimal amount;     // 목표 금액

    public boolean isAmountValid() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }


}
