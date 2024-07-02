package com.bank.bankingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.bankingapp.entity.Transactions;
import java.util.List;
import java.util.Optional;


public interface TransactionRepository extends CrudRepository<Transactions , Long> {
   
}
