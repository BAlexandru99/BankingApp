package com.bank.bankingapp.exceptions;

public class NotEnoughExceprion extends RuntimeException {
    public NotEnoughExceprion(){
        super("You dont have enough money!");
    }
}