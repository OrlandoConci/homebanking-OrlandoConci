package com.mindhub.homebanking.dtos;

public record CreationTransactionDTO(Double amount, String description, String numberOrigin, String numberDestination) {
}
