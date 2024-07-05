package com.bank.bankingapp.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transaction;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.service.UserSevice;
import org.springframework.web.bind.annotation.PutMapping;

// Marcam clasa ca fiind un controller Spring care gestioneaza rutele sub "/login"
@Controller
@RequestMapping("/login")
public class AdminController {

    @Autowired
    private UserSevice userSevice;

    // Ruta pentru afisarea paginii principale
    @GetMapping("/")
        public String getHome(Model model){
            return "home";// Returnam numele paginii template pentru pagina de start
        }


    // Ruta pentru adaugarea unui utilizator nou
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        // Verificam dacă exista deja un utilizator cu acelasi nume de utilizator
        User existingUser = userSevice.findByUsername(user.getUsername());
        if (existingUser != null) {
            //// Returnam o eroare cu codul 406 Not Acceptable daca utilizatorul exista deja
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with username: " + user.getUsername());
        } else {
             // Salvam utilizatorul nou în baza de date si returnam un mesaj de succes
            userSevice.saveUser(user);
            return ResponseEntity.ok("User added successfully!");
        }
    }

    // Ruta pentru actualizarea unui utilizator existent după ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id , @RequestBody User user ){
        // Actualizam utilizatorul după ID si returnam un mesaj de succes
        userSevice.updateById(id, user);
        return ResponseEntity.ok("User updated successfully!");
    }
    
    // Ruta pentru stergerea unui utilizator după ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        // Stergem utilizatorul dupa ID si returnam codul 204 No Content
        userSevice.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Ruta pentru gestionarea autentificării utilizatorilor
    @PostMapping("/submit")
    public String submitForm(@RequestParam String username, @RequestParam String password, Model model , HttpSession session){
        // Autentificam utilizatorul pe baza numelui de utilizator si parolei
        User user = userSevice.authenticate(username, password);
            if(user != null){
                // Capitalizam primul caracter al numelui de utilizator pentru afisare
                String capitalizedUsername = capitalizeFirstLetter(user.getUsername());
                model.addAttribute("username", capitalizedUsername);
                model.addAttribute("suma", user.getSuma());
                session.setAttribute("loggedInUsername", username);
                session.setAttribute("loggedInUser", user);
            
            return "managePanel"; // Returnam pagina de gestionare a utilizatorului dupa autentificare
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "home"; // Returnam pagina de start cu mesaj de eroare
        }
    }

    // Ruta pentru afisarea panoului de gestionare a tranzactiilor
    @GetMapping("/submit")
    public String getTransactionPanel(Model model, HttpSession session) {
    // Obținem utilizatorul autentificat din sesiune
    User user = (User) session.getAttribute("loggedInUser");
    if (user != null) {
        // Capitalizam primul caracter al numelui de utilizator pentru afisare
        String capitalizedUsername = capitalizeFirstLetter(user.getUsername());
        model.addAttribute("username", capitalizedUsername);
        model.addAttribute("suma", user.getSuma());
    } else {
        return "redirect:/login/"; // Redirectionam catre pagina de start dacă nu exista utilizator autentificat
    }
    return "managePanel";  // Returnam pagina de gestionare a utilizatorului
}

    // Metoda auxiliara pentru capitalizarea primului caracter al unei siruri
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
