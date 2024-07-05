package com.bank.bankingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.bankingapp.entity.Transactions;
import com.bank.bankingapp.entity.User;

import java.util.List;
import java.util.Optional;

//CRUD (Create, Read, Update, Delete) 
//<Transactions, Long>: specifică tipul entitatii și tipul identificatorului acesteia. 
//In acest caz, entitatea este Transactions, iar tipul identificatorului este Long

public interface TransactionRepository extends CrudRepository<Transactions , Long> {
    
    List<Transactions> findByUser_Username(String username);

    //Metoda va găsi toate tranzacțiile (Transactions) care corespund unui anumit criteriu.
    //-findBy: prefixul metodei care indică faptul că se dorește căutarea unor înregistrări.
    //-User_Username: denumește calea către câmpul pentru care se face căutarea. 
    //În acest caz, User este relația definită în entitatea Transactions și Username este câmpul username din entitatea User. 
    //Convenția Spring Data JPA permite utilizarea acestor denumiri pentru a genera automat codul SQL necesar pentru interogare.
}
