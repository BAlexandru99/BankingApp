package com.bank.bankingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.repository.TransactionRepository;
import com.bank.bankingapp.repository.UserRepository;

// Marcam clasa ca fiind un serviciu Spring
@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Transactions addTransactions(Transactions transactions , String username) {
        // Gasim utilizatorul pe baza numelui de utilizator
        User user = userRepository.findByUsername(username);
        // Setăm utilizatorul pentru tranzacția curentă
        transactions.setUser(user);
        // Salvăm tranzacția în baza de date
        return transactionRepository.save(transactions);
    }

    @Override
    public List<Transactions> getTransactionsByUser(String username) {
        // Găsim și returnăm toate tranzacțiile pentru un anumit utilizator
        return transactionRepository.findByUser_Username(username);
    }
}
