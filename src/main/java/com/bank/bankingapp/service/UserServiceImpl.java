package com.bank.bankingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserSevice{

    @Autowired
    UserRepository userRepository;

    
      @Autowired
      BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
