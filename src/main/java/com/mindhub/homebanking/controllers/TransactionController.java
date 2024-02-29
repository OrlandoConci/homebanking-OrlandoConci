package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CreationTransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> newTransaction(@RequestBody CreationTransactionDTO creationTransactionDTO) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client currentClient = clientRepository.findByEmail(userMail);

        if(!accountRepository.existsByNumber(creationTransactionDTO.numberDestination())) {
            return new ResponseEntity<>("the destination account does not exist", HttpStatus.FORBIDDEN);
        }

        if(!accountRepository.existsByNumber(creationTransactionDTO.numberOrigin())) {
            return new ResponseEntity<>("the origin account does not exist", HttpStatus.FORBIDDEN);
        }

        if(creationTransactionDTO.numberDestination().isBlank()) {
            return new ResponseEntity<>("The destination number field must not be empty " , HttpStatus.FORBIDDEN);
        }

        if(creationTransactionDTO.numberOrigin().isBlank()) {
            return new ResponseEntity<>("The origin number field must not be empty " , HttpStatus.FORBIDDEN);
        }

        if(creationTransactionDTO.amount() == null || creationTransactionDTO.amount().toString().isBlank()) {
            return new ResponseEntity<>("The mount field must not be empty " , HttpStatus.FORBIDDEN);
        }

        if(creationTransactionDTO.description().isBlank()) {
            return new ResponseEntity<>("The description field must not be empty " , HttpStatus.FORBIDDEN);
        }

        if(!accountRepository.existsAccountByNumberAndOwner(creationTransactionDTO.numberOrigin(), currentClient)) {
            return new ResponseEntity<>("You are not the owner of the source account", HttpStatus.FORBIDDEN);
        }

        if(creationTransactionDTO.amount() <= 0) {
            return new ResponseEntity<>("You cannot transfer an amount equal to or less than zero", HttpStatus.FORBIDDEN);
        }

        if(creationTransactionDTO.numberOrigin().equals(creationTransactionDTO.numberDestination())) {
            return new ResponseEntity<>("the account numbers are the same", HttpStatus.FORBIDDEN);
        }

        if(accountRepository.findAccountByNumberAndOwner(creationTransactionDTO.numberOrigin(), currentClient)
                .getBalance() < creationTransactionDTO.amount()) {
            return new ResponseEntity<>("You do not have enough amount to carry out the transaction", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit = new Transaction(TransactionType.DEBIT, (-creationTransactionDTO.amount()),
                creationTransactionDTO.description(), LocalDateTime.now());

        Transaction transactionCredit = new Transaction(TransactionType.CREDIT, creationTransactionDTO.amount(),
                creationTransactionDTO.description(), LocalDateTime.now());

        Account accountOrigin = accountRepository.findAccountByNumberAndOwner(creationTransactionDTO.numberOrigin(), currentClient);
        accountOrigin.setBalance(accountOrigin.getBalance() - creationTransactionDTO.amount());
        accountOrigin.addTransactiosns(transactionDebit);

        Account accountDestination = accountRepository.findAccountByNumber(creationTransactionDTO.numberDestination());
        accountDestination.setBalance(accountDestination.getBalance() + creationTransactionDTO.amount());
        accountDestination.addTransactiosns(transactionCredit);

        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestination);

        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit); //Probar acá con null

        return new ResponseEntity<>("transaction created successfully", HttpStatus.CREATED);
    }




}