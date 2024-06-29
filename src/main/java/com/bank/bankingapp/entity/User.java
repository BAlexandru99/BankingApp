package com.bank.bankingapp.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;
    @Column(name = "username" , nullable = true)
    private String username;
    @Column(name = "password" , nullable = true)
    private String password;
    @Column(name = "Suma" , nullable = true)
    private int suma;

}
