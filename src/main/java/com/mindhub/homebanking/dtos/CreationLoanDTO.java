package com.mindhub.homebanking.dtos;

public record CreationLoanDTO(String name, Double amount, Integer installments, String numberAccount) {
}
