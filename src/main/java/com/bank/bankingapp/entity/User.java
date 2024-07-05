package com.bank.bankingapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Getter
@Setter
// Generare constructor cu parametri
@AllArgsConstructor
// Generare constructor fără parametri
@NoArgsConstructor
@Table(name = "users")// Setarea Numelui tabelului

public class User implements Serializable {

    // Identificatorul unic al tranzacției, generat automat
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;

    //Generarea fiecari coloane de tabel + atribute
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password" , nullable = true)
    private String password;

    @Column(name = "Suma" , nullable = true)
    private int suma;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Transactions> transactions;

}
