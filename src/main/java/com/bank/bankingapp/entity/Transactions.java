package com.bank.bankingapp.entity;

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
@AllArgsConstructor
@NoArgsConstructor

public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "username" , referencedColumnName = "username")
    private User user;
    @Column(name = "suma" , nullable = false)
    private Long suma;
    @Column(name = "moneda" , nullable = false)
    private String moneda;
    @Column(name = "message")
    private String message;
}
