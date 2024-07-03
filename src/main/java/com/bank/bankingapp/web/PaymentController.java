package com.bank.bankingapp.web;

import javax.servlet.http.HttpSession;
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
import com.bank.bankingapp.exceptions.NotEnoughExceprion;
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
                                 @RequestParam int suma, 
                                 @RequestParam String moneda, 
                                 @RequestParam String descriere, 
                                 HttpSession session,
                                 Model model) {
        

        
        String loggedInUsername = (String) session.getAttribute("loggedInUsername");
        if (loggedInUsername.equals(destinat)) {
            model.addAttribute("error", "Nu puteți trimite bani către dvs.");
            return "transactionPanel";
        }

        User destinatar = userSevice.findByUsername(destinat);
        if (destinatar == null) {
            model.addAttribute("error", "Destinatarul nu există în baza de date.");
            return "transactionPanel";
        }
        

        Transactions transaction = new Transactions();
        transaction.setUser(destinatar);
        transaction.setSuma(suma);
        transaction.setMoneda(moneda);
        transaction.setMessage(descriere);

        try {
            transactionsService.addTransactions(transaction, destinat);
            userSevice.updateSum(destinat, suma, loggedInUsername);
            model.addAttribute("message", "Tranzacția a fost adăugată cu succes!");

            
        } catch (NotEnoughExceprion e) {
            transaction.setMessage("This transaction was refused. \n Cause(Insuficient Founds!) ");
            transactionsService.addTransactions(transaction, destinat);
            model.addAttribute("error", "You don't have enough money!");
        }
        return "transactionPanel";
    }
}    