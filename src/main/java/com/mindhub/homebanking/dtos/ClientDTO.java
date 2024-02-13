package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;

public class ClientDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String mail;

    private Set<CardDTO> cards = new HashSet<>();

    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> clientLoans = new HashSet<>();

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.mail = client.getMail();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(java.util.stream.Collectors.toSet());
        this.clientLoans = client.getClientloans().stream().map(ClientLoanDTO::new).collect(java.util.stream.Collectors.toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(java.util.stream.Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getClientloans() {
        return clientLoans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
