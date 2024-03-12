package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.controllers.CurrentController;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.CardRepository;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@SpringBootTest
public class HomebankingApplicationTests {

    @Autowired
    private CardRepository cardRepository;

    @Test
    public void contectLoads() {
    }

    @RepeatedTest(5)
    public void testGenerateCVV() {
        String cvv = Account.getRandomNumber(100, 1000);
        assertThat(cvv.length(), equalTo(3));
    }

    @Test
    public void testGenerateNumberCard(){
        String number = CurrentController.generateNumberCard();
        assertThat(number.length(), equalTo(19));
    }
}
