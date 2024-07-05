package com.bank.bankingapp.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.service.TransactionsService;
import com.bank.bankingapp.service.UserSevice;

@Controller
public class HistoryController {

    @Autowired
    TransactionsService transactionsService;

    @Autowired 
    UserSevice userSevice;

    // Ruta pentru afisarea istoricului tranzactiilor
    @GetMapping("/transactionHistory")
    public String showTransactionHistory(Model model, HttpSession session) {
        // Obținem numele de utilizator al utilizatorului autentificat din sesiune
        String loggedInUsername = (String) session.getAttribute("loggedInUsername");
        if (loggedInUsername != null) {
            // Obtinem lista de tranzactii pentru utilizatorul autentificat
            List<Transactions> transactionsList = transactionsService.getTransactionsByUser(loggedInUsername);
            model.addAttribute("transactionsList", transactionsList); // Adăugam lista de tranzactii în model
        } else {
             // Redirectionam catre pagina de autentificare dacă nu există utilizator autentificat
            return "redirect:/login/"; 
        }
        // Returnam pagina de istoric al tranzactiilor pentru afișare
        return "transactionHistory";
    }
    
}
