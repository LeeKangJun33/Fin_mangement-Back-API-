package com.example.fin_mangement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BudgetDto {

   @JsonProperty("name")
    private String budgetName;
    @JsonProperty("amount")
    private double amount;
}
