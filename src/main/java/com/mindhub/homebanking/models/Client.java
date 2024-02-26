package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role = "USER";

    @OneToMany(mappedBy="cardHolder", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<ClientLoan> clientloans = new HashSet<>();

    public Client() { }

    public Client(String firstName, String lastName, String mail, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = mail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<ClientLoan> getClientloans() {
        return clientloans;
    }

    public void setClientloans(Set<ClientLoan> clientloans) {
        this.clientloans = clientloans;
    }

    public void addAccounts(Account account) {
        account.setOwner(this);
        this.accounts.add(account);
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientloans.add(clientLoan);
    }

    public void addCard(Card card) {
        card.setCardHolder(this);
        cards.add(card);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + email + '\'' +
                ", accounts=" + accounts +
                ", clientloans=" + clientloans +
                '}';
    }
}
