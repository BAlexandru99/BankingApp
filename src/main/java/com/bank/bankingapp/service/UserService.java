package com.bank.bankingapp.service;

import com.bank.bankingapp.entity.User;

public interface UserService {

    User authenticate(String username, String password);

    User saveUser(User user);

    void deleteUserByUsername(String username);

    User findByUsername(String username);

    User updateById(String id, User user);

    void updateSum(String username, int sum, String transactionUsername);

    User refreshUser(String username);
}
