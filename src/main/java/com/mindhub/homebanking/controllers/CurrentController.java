package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CreationCartDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.models.Account.getRandomNumber;
import static io.jsonwebtoken.lang.Collections.size;

@RestController
@RequestMapping("/api/clients/current")
public class CurrentController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/")
    public ResponseEntity<?> getClient() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        return ResponseEntity.ok(new ClientDTO(client));
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> postNewAccount() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        if (client.getRole().equals("USER")) {
            if (client.getAccounts().size() == 3) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Limit reached, you cannot have more than 3 accounts");
            }

            Account newAccount = createAccount();

            client.addAccounts(newAccount);
            clientRepository.save(client);
            accountRepository.save(newAccount);
            return ResponseEntity.status(HttpStatus.CREATED).body("Your account was created successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to perform this action");
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccountsCurrent() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        return new ResponseEntity<>(client.getAccounts().stream().map(AccountDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    public Account createAccount() {
        String numberAccount = "";
        do {
            numberAccount = "VIN-" + getRandomNumber(10000000, 100000000);
        } while (accountRepository.existsByNumber(numberAccount));

        return new Account(numberAccount, LocalDate.now(), 0);
    }

    @PostMapping("/cards")
    public ResponseEntity<?> creationCards(@RequestBody CreationCartDTO creationCartDTO) {

        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        List<Boolean> exists = client.getCards().stream().map(card -> card.getColor() == creationCartDTO.colorType() &&
                card.getType() == creationCartDTO.transactionType()).toList();

        if (exists.contains(true)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Limit reached, you cannot have more than 1 cards equals");
        }

        if(creationCartDTO.transactionType().equals("")) {
            return new ResponseEntity<>("The card type field must not be empty " , HttpStatus.FORBIDDEN);
        }

        if(creationCartDTO.colorType().equals("")) {
            return new ResponseEntity<>("The card type field must not be empty " , HttpStatus.FORBIDDEN);
        }

        String number = "";
        do {
            number = getRandomNumber(1000, 2001) + '-' + getRandomNumber(2001, 3001) + '-' + getRandomNumber(3001, 4001) + '-' + getRandomNumber(4001, 5001);
        } while (cardRepository.existsByNumber(number));

        Card newCard = new Card(creationCartDTO.transactionType(), creationCartDTO.colorType(), number, getRandomNumber(100, 1000), LocalDate.now(), LocalDate.now().plusYears(5));

        client.addCard(newCard);

        clientRepository.save(client);
        cardRepository.save(newCard);

        return ResponseEntity.status(HttpStatus.CREATED).body("Your card was created successfully");
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getAllCardsCurrent() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        return new ResponseEntity<>(client.getCards().stream().map(CardDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }
}
