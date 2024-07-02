package com.bank.bankingapp.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.service.TransactionsService;
import com.bank.bankingapp.service.UserSevice;

@Controller
public class PaymentController {

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private UserSevice userSevice;

    @GetMapping("/transactionPanel")
    public String showTransactionPanel(Model model) {
        return "transactionPanel";
    }
    
    @PostMapping("/addTransaction")
    public String addTransaction(@RequestParam String destinat, 
                                 @RequestParam Long suma, 
                                 @RequestParam String moneda, 
                                 @RequestParam String descriere, 
                                 Model model){
        
        User destinatar = userSevice.findByUsername(destinat);
        if (destinatar == null) {
            throw new IllegalArgumentException("Destinatarul nu există în baza de date.");
        }
        
        Transactions transaction = new Transactions();
        transaction.setUser(destinatar);
        transaction.setSuma(suma);
        transaction.setMoneda(moneda);
        transaction.setMessage(descriere);

        transactionsService.addTransactions(transaction, destinat);
        model.addAttribute("message", "Tranzacția a fost adăugată cu succes!");
        return "transactionPanel";
    }
}