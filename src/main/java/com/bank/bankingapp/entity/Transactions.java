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


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExpeditor() {
        return this.expeditor;
    }

    public void setExpeditor(String expeditor) {
        this.expeditor = expeditor;
    }

    public int getSuma() {
        return this.suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
