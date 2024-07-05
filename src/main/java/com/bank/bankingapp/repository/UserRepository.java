package com.bank.bankingapp.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.bankingapp.entity.User;

@Repository

//<User, Long>: specifica tipul entitatii și tipul identificatorului acesteia. 
public interface UserRepository extends CrudRepository<User, Long> {
   User findByUsername(String username);
   //findByUsername: este o metoda personalizata care urmeaza conventiile Spring Data JPA. 
   //Metoda va gasi un utilizator (User) pe baza numelui de utilizator (username).
   
   //Asigură că toate operațiunile dintr-o metodă sunt executate complet sau nu sunt executate deloc.
   //Dacă apare o eroare, Spring face rollback, adica anulează schimbarile.
   @Transactional
   //@Modifying: indica faptul ca metoda va modifica datele din baza de date (nu doar le va citi).
   @Modifying

   //@Query("UPDATE User u SET u.suma = :suma WHERE u.username = :username"): definește interogarea personalizata
   //-UPDATE User u SET u.suma = :suma WHERE u.username = :username: interogarea JPQL care actualizează câmpul suma al utilizatorului specificat de username.
   //-:suma și :username: parametri care vor fi înlocuiți cu valorile furnizate la apelarea metodei.
   @Query("UPDATE User u SET u.suma = :suma WHERE u.username = :username")
   void updateSuma(int suma, String username);
}
