package com.bank.bankingapp.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.bankingapp.entity.User;

public interface UserSevice {
    User saveUser(User user);
    User authenticate(String username, String password);
    void deleteById(Long id);
    User findByUsername(String username);
    User updateById(Long id, User user);
    public void updateSum(String username, int sum , String transationUser);
    User refreshUser(String username);
}
