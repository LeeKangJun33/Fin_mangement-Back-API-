package com.example.fin_mangement.service;

import com.example.fin_mangement.entity.Transaction;
import com.example.fin_mangement.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private TransactionRepository transactionRepository;


   public  List<Transaction> getTransactionsByUserId(Long userId) {
    return transactionRepository.findByUserId(userId);
   }

   public Transaction addTransaction(Transaction transaction) {
       return transactionRepository.save(transaction);
   }
}
