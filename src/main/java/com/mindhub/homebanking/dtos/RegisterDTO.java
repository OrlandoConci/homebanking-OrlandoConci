package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

public record RegisterDTO(String firstName, String lastName, String email, String password) {

}
