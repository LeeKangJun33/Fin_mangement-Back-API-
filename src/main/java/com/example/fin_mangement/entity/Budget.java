package com.example.fin_mangement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "budget")
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "budgetName")
    private String budgetName;

    private String userId;
}
