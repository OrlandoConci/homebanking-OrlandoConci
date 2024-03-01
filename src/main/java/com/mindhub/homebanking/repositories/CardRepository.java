package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ColorType;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Boolean existsByNumber(String number);

    Boolean existsCardByTypeAndColorAndCardHolder(TransactionType type, ColorType color, Client cardHolder);
}
