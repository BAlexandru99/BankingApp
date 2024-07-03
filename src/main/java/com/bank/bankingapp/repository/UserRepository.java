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
public interface UserRepository extends CrudRepository<User, Long> {
   User findByUsername(String username);
   
   @Transactional
   @Modifying
   @Query("UPDATE User u SET u.suma = :suma WHERE u.username = :username")
   void updateSuma(int suma, String username);
}
