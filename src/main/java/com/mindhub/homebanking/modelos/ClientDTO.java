package com.mindhub.homebanking.modelos;

public class ClientDTO {
    private Long id;
    private String name;
    private String mail;

    public ClientDTO(Client user) {
        this.id = user.getId();
        this.name = user.getFirstName();
        this.mail = user.getMail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }
}
