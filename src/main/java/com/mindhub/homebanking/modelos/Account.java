package com.mindhub.homebanking.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    private Client owner;
    private String number;

    private LocalDate date;

    private double balance;

    @OneToMany(mappedBy="ownerTransaction", fetch= FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {
    }

    public Account(String number, LocalDate date, double balance) {
        this.number = number;
        this.date = date;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransactiosns(Transaction transaction) {
        transaction.setOwnerTransaction(this);
        this.transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
