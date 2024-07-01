package com.bank.bankingapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.bankingapp.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   User findByUsername(String username);
}
