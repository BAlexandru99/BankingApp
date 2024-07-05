package com.bank.bankingapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.exceptions.NotEnoughExceprion;
import com.bank.bankingapp.exceptions.UserNotFoundException;
import com.bank.bankingapp.repository.UserRepository;

// Marcam clasa ca fiind un serviciu Spring
@Service
public class UserServiceImpl implements UserSevice{

    @Autowired
    UserRepository userRepository;

     // Injectam automat o instanță de BCryptPasswordEncoder pentru gestionarea parolelor
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


       // Metoda pentru autentificarea utilizatorului  
      @Override
      public User authenticate(String username, String password) {
          // Cautam utilizatorul după numele de utilizator
          User user = userRepository.findByUsername(username);
            // Verificăm dacă utilizatorul există și dacă parola introdusa se potrivește cu cea stocată (criptată)
          if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
              return user;
          }
          return null; // Returnam null dacă autentificarea esueaza
      }



    // Metoda pentru salvarea unui nou utilizator
    @Override
    public User saveUser(User user) {
        // Criptam parola utilizatorului inainte de a o salva in baza de date
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
        // Salvam utilizatorul in baza de date
        return userRepository.save(user);
    }

    // Metoda pentru stergerea unui utilizator după ID
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // Metoda pentru gasirea unui utilizator dupa numele de utilizator
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    // Metoda pentru actualizarea unui utilizator dupa ID
    @Override
    public User updateById(Long id, User user) {
        // Cautam utilizatorul după ID
        Optional<User> optionalUser = userRepository.findById(id);
        // Verificam dacă utilizatorul exista
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Actualizam campurile utilizatorului existent
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            // // Adaugam alte campuri care trebuie actualizate
            return userRepository.save(existingUser);
        } else throw new UserNotFoundException(id); // Arunca o exceptie daca user-ul nu e gasit
    }



    @Override
    // Metoda pentru actualizarea sumei unui utilizator dupa numele de utilizator
    public void updateSum(String username , int sum , String transationUsername) {
        
        // Gasim utilizatorul destinatar si utilizatorul tranzactiei dupa numele de utilizator
        User user = userRepository.findByUsername(username);
        User transationUser = userRepository.findByUsername(transationUsername);

        // Calculam suma finala a utilizatorului tranzactiei după retragere
        int userInitial = transationUser.getSuma();
        int userFinalSum = userInitial - sum;

        // Verificam dacă utilizatorul tranzactiei are suficienti bani
        if(transationUser.getSuma() >= sum){
            // Actualizam suma utilizatorului tranzactiei
            userRepository.updateSuma(userFinalSum, transationUsername);
            // Actualizam suma utilizatorului destinatar
            int initalSum  = user.getSuma();
            int finalSum = initalSum + sum;
            userRepository.updateSuma(finalSum, username);
        }else{
            // Aruncam o exceptie dacă utilizatorul tranzactiei nu are suficienti bani
            throw new NotEnoughExceprion();
        }
    }
}
