package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.modelos.Account;
import com.mindhub.homebanking.modelos.Client;

import java.util.Set;

public class ClientDTO {
    private Long id;
    private String firstName;

    private String lastName;
    private String mail;

    private Set<AccountDTO> accounts;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.mail = client.getMail();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(java.util.stream.Collectors.toSet());
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
}
