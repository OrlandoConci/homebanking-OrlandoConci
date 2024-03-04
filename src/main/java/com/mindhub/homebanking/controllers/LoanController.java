package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CreationLoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.models.TransactionType.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllAvailableLoans() {
        List<Loan> loans = loanRepository.findAll();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> requestLoan(@RequestBody CreationLoanDTO creationLoanDTO) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        if (creationLoanDTO.name() == null || creationLoanDTO.name().isBlank()) {
            return new ResponseEntity<>("The name field cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if (creationLoanDTO.amount() == null || creationLoanDTO.amount().toString().isBlank()) {
            return new ResponseEntity<>("The amount field cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if (creationLoanDTO.installments() == null || creationLoanDTO.installments().toString().isBlank()) {
            return new ResponseEntity<>("The installments field cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if (creationLoanDTO.numberAccount() == null || creationLoanDTO.numberAccount().isBlank()) {
            return new ResponseEntity<>("The number account field cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        //------------------------------------------------
        if (!loanRepository.existsByName(creationLoanDTO.name())) {
            return new ResponseEntity<>("Non-existent loan", HttpStatus.FORBIDDEN);
        }

        Loan chosenLoan = loanRepository.findByName(creationLoanDTO.name());

        if (chosenLoan.getMaxAmount() < creationLoanDTO.amount()) {
            return new ResponseEntity<>("Amount exceeded the maximum limit", HttpStatus.FORBIDDEN);
        }

        if (!chosenLoan.getPayments().contains(creationLoanDTO.installments())) {
            return new ResponseEntity<>("Amount of installments not available for this loan", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsByNumber(creationLoanDTO.numberAccount())) {
            return new ResponseEntity<>("The destination account does not exist.", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsAccountByNumberAndOwner(creationLoanDTO.numberAccount(), client)) {
            return new ResponseEntity<>("The destination account does not belong to you.", HttpStatus.FORBIDDEN);
        }

        ClientLoan currentLoan = new ClientLoan(creationLoanDTO.name(),
                (creationLoanDTO.amount() + (creationLoanDTO.amount() * 20 / 100)), creationLoanDTO.installments());
        client.addClientLoan(currentLoan);
        chosenLoan.addClientLoan(currentLoan);

        Account destinationAccount = accountRepository.findAccountByNumberAndOwner(creationLoanDTO.numberAccount(), client);

        Transaction currentTransaction = new Transaction(CREDIT, creationLoanDTO.amount(),
                creationLoanDTO.name() + ", loan approved", LocalDateTime.now());

        destinationAccount.addTransactiosns(currentTransaction);
        destinationAccount.setBalance(destinationAccount.getBalance() + creationLoanDTO.amount());

        clientRepository.save(client);
        accountRepository.save(destinationAccount);
        transactionRepository.save(currentTransaction);
        clientLoanRepository.save(currentLoan);

        return new ResponseEntity<>("Loan created successfully", HttpStatus.OK);
    }
}
