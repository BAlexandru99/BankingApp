package com.bank.bankingapp.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId){
        super("The user with id of " + userId + " cannot be found!");
    }
}
