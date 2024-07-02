package com.bank.bankingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.repository.TransactionRepository;
import com.bank.bankingapp.repository.UserRepository;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Transactions addTransactions(Transactions transactions , String username) {
        User user = userRepository.findByUsername(username);
        transactions.setUser(user);
        return transactionRepository.save(transactions);
    }
}
