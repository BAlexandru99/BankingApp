package com.bank.bankingapp.web;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.exceptions.NotEnoughExceprion;
import com.bank.bankingapp.service.TransactionsService;
import com.bank.bankingapp.service.UserSevice;
import org.springframework.web.bind.annotation.RequestBody;


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
                                 @RequestParam String operation,
                                 HttpSession session,
                                 Model model) {


        String loggedInUsername = (String) session.getAttribute("loggedInUsername");
        User loggednInUser = userSevice.findByUsername(loggedInUsername);
        User destinatar = userSevice.findByUsername(destinat);

        if(operation.equals("primeste")){         
            if (loggedInUsername.equals(destinat)) {
                model.addAttribute("error", "Nu puteți face cerere către dvs.");
                return "transactionPanel"; // Returnam pagina panoului de tranzactii cu mesajul de eroare
            }
            if (destinatar == null) {
                model.addAttribute("error", "Destinatarul nu există în baza de date.");
                return "transactionPanel";// Returnam pagina panoului de tranzactii cu mesajul de eroare
            }

            Transactions transactions = new Transactions();
            transactions.setUser(destinatar);
            transactions.setSuma(suma);
            transactions.setExpeditor(loggedInUsername);
            transactions.setMessage(descriere);

            session.setAttribute("requestUser", transactions.getExpeditor());
            session.setAttribute("requestSum", suma);
            session.setAttribute("requestDesc", descriere);
            session.setAttribute("destinat", destinat);

            model.addAttribute("message", "Cererea a fost inregistrata cu succes!");
            return "transactionPanel";
        }

        
        // Verificam dacă utilizatorul incearca sa trimita bani către propriul cont
        if (loggedInUsername.equals(destinat)) {
            model.addAttribute("error", "Nu puteți trimite bani către dvs.");
            return "transactionPanel"; // Returnam pagina panoului de tranzactii cu mesajul de eroare
        }

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
            User refreshedUser = userSevice.refreshUser(loggedInUsername);
            session.setAttribute("loggedInUser", refreshedUser);
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