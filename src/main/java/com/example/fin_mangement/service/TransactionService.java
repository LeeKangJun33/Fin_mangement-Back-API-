package com.example.fin_mangement.service;

import com.example.fin_mangement.entity.Transaction;
import com.example.fin_mangement.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepository;

    //거래추가
    public Transaction addTransaction(Long userId, String description, double amount, LocalDateTime date, String category){
        Transaction transaction = new Transaction(userId, description, amount, date, category);
        return transactionRepository.save(transaction);
    }
    //사용자별 거래 조회
    public List<Transaction> getTransactionsByUserId(Long userId){
        return transactionRepository.findByUserId(userId);
    }

}
