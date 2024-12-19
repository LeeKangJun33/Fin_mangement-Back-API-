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
    private String startDate;  // 시작 날짜

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDate;    // 종료 날짜

    private BigDecimal amount;     // 목표 금액

    public boolean isAmountValid() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    // 날짜 유효성 검사 메서드 추가
    public boolean areDatesValid() {
        if (startDate == null || endDate == null) {
            return false;
        }
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return !start.isAfter(end);  // 시작 날짜가 종료 날짜보다 늦지 않으면 true
    }

    // 날짜를 LocalDate로 변환
    public LocalDate getStartDateAsLocalDate() {
        return LocalDate.parse(startDate);
    }

    public LocalDate getEndDateAsLocalDate() {
        return LocalDate.parse(endDate);
    }


}
