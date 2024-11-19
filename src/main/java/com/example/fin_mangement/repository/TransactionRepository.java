package com.example.fin_mangement.repository;

import com.example.fin_mangement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String > {

    List<Transaction> findByUsername(String  username);
}
