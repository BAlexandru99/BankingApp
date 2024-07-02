package com.bank.bankingapp.service;
import javax.transaction.Transaction;
import javax.validation.Valid;

import com.bank.bankingapp.entity.Transactions;

public interface TransactionsService {
    Transactions addTransactions(Transactions transactions , String username);

}
