package com.bank.bankingapp.entity;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
// Generare constructor cu parametri
@AllArgsConstructor
// Generare constructor fără parametri
@NoArgsConstructor
// Nu setam numele deorece se va seta automat numele clasei


public class Transactions {

    // Identificatorul unic al tranzacției, generat automat
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;

    // Relație ManyToOne cu entitatea User, nu poate fi null
    @ManyToOne(optional = false)
    // Specifică coloana de legătură din tabela User, care este 'username'
    @JoinColumn(name = "username" , referencedColumnName = "username")
    private User user;


    //Generarea fiecari coloane de tabel + atribute
    @Column(name = "expeditor" , nullable = false)
    private String expeditor;
    
    @Column(name = "suma" , nullable = false)
    private int suma;

    @Column(name = "moneda" , nullable = false)
    private String moneda;

    @Column(name = "message")
    private String message;

    @Column(name = "data", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime timestamp;
}
