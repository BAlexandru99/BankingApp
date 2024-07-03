package com.bank.bankingapp.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
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

import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.service.UserSevice;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/login")
public class AdminController {

    @Autowired
    private UserSevice userSevice;

    @GetMapping("/")
        public String getHome(Model model){
            return "home";
        }


    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        User existingUser = userSevice.findByUsername(user.getUsername());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with username: " + user.getUsername());
        } else {
            userSevice.saveUser(user);
            return ResponseEntity.ok("User added successfully!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id , @RequestBody User user ){
        userSevice.updateById(id, user);
        return ResponseEntity.ok("User updated successfully!");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userSevice.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/submit")
    public String submitForm(@RequestParam String username, @RequestParam String password, Model model , HttpSession session){
        User user = userSevice.authenticate(username, password);
            if(user != null){
            String capitalizedUsername = capitalizeFirstLetter(user.getUsername());
            model.addAttribute("username", capitalizedUsername);
            model.addAttribute("suma", user.getSuma());
            session.setAttribute("loggedInUsername", username);
            //session.setAttribute("sumOfTheUser", user.getSuma());

            return "managePanel";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "home";
        }
    }
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
