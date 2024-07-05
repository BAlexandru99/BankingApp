package com.bank.bankingapp.service;
import java.util.List;

import javax.transaction.Transaction;
import javax.validation.Valid;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;

public interface TransactionsService {
    Transactions addTransactions(Transactions transactions , String username);
    List<Transactions> getTransactionsByUser(String username);
}

