package com.example.fin_mangement.repository;

import com.example.fin_mangement.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {

    Optional<Budget> findTopByOrderByCreatedAtDesc();
}
