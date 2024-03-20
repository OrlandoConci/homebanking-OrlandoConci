//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import static org.hamcrest.Matchers.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
//public class RepositoriesTest {
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//    @Autowired
//    private CardRepository cardRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Test
//    public void existLoans() {
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans,is(not(empty())));
//    }
//
//    @Test
//    public void existPersonalLoan() {
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem((hasProperty("name", is("Personal")))));
//    }
//
//    @Test
//    public void haveCards(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, is(not(empty())));
//    }
//
//    @Test
//    public void jhonHasOneCard() {
//        Boolean card = cardRepository.existsByNumber("32131315");
//        assertThat(card, is(false));
//    }
//    @Test
//    public void existAccount() {
//        List<Account> accounts = accountRepository.findAll();
//        assertThat(accounts,is(not(empty())));
//    }
//    @Test
//    public void existClient() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients,is(not(empty())));
//    }
//    @Test
//    public void existTranscation() {
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions,is(not(empty())));
//    }
//
//}
