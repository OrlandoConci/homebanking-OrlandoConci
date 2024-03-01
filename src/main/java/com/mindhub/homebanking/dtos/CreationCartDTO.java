package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ColorType;
import com.mindhub.homebanking.models.TransactionType;

public record CreationCartDTO(String colorType, String transactionType) {
}
