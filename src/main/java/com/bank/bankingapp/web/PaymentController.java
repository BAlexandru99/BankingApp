package com.bank.bankingapp.web;

import java.time.LocalDateTime;
import java.util.List;

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
        

        // Obtinem numele de utilizator al utilizatorului autentificat din sesiune
        String loggedInUsername = (String) session.getAttribute("loggedInUsername");
        
        // Verificam dacă utilizatorul incearca sa trimita bani către propriul cont
        if (loggedInUsername.equals(destinat)) {
            model.addAttribute("error", "Nu puteți trimite bani către dvs.");
            return "transactionPanel"; // Returnam pagina panoului de tranzactii cu mesajul de eroare
        }

        // Cautam utilizatorul destinatar în baza de date
        User destinatar = userSevice.findByUsername(destinat);
        if (destinatar == null) {
            model.addAttribute("error", "Destinatarul nu există în baza de date.");
            return "transactionPanel";// Returnam pagina panoului de tranzactii cu mesajul de eroare
        }
        
        // Cream un obiect Transactions pentru noua tranzactie

        Transactions transaction = new Transactions();
        //Setam compurile entitatii Transactions
        transaction.setExpeditor(loggedInUsername);
        transaction.setUser(destinatar);
        transaction.setSuma(suma);
        transaction.setMoneda(moneda);
        transaction.setMessage(descriere);
        transaction.setTimestamp(LocalDateTime.now());

        try {
            // Adaugam tranzactia în baza de date
            transactionsService.addTransactions(transaction, destinat);
            // Actualizam suma utilizatorului destinatar
            userSevice.updateSum(destinat, suma, loggedInUsername);
            // Afisam mesajul de succes
            model.addAttribute("message", "Tranzacția a fost adăugată cu succes!");

            
        } catch (NotEnoughExceprion e) {
            // In cazul în care nu există suficienti bani pentru tranzactie, gestionam excepția
            transaction.setMessage("This transaction was refused. \n Cause(Insuficient Founds!) ");
            transactionsService.addTransactions(transaction, destinat);
            model.addAttribute("error", "You don't have enough money!");
        }
        return "transactionPanel";  // Returnam pagina panoului de tranzactii
    }

}    