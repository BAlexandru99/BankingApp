package com.bank.bankingapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.exceptions.UserNotFoundException;
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


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public User updateById(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            // Add any other fields that need to be updated
            return userRepository.save(existingUser);
        } else throw new UserNotFoundException(id); // Or throw an exception if user not found
    }
}
