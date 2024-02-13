package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static com.mindhub.homebanking.models.ColorType.*;
import static com.mindhub.homebanking.models.TransactionType.*;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return args -> {
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			Client silvia = new Client("Silvia", "Montiel", "silvia@outlook.es");

			clientRepository.save(melba);
			clientRepository.save(silvia);


			Account vin001 = new Account("VIN001", LocalDate.now(), 5000);
			Account vin002 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Account vin003 = new Account("VIN003", LocalDate.now(), 4500);
			Account vin004 = new Account("VIN004", LocalDate.now().plusDays(1), 8000);

			melba.addAccounts(vin001);
			melba.addAccounts(vin002);
			silvia.addAccounts(vin003);
			silvia.addAccounts(vin004);

			accountRepository.save(vin001);
			accountRepository.save(vin002);
			accountRepository.save(vin003);
			accountRepository.save(vin004);


			Transaction m001 = new Transaction(DEBIT, -2325.55, "Deuda Mercado Pago.", LocalDateTime.now());
			Transaction m002 = new Transaction(CREDIT, 7405.31, "Comida del gato.", LocalDateTime.now());
			Transaction m003 = new Transaction(DEBIT, -8340.35, "Empanadas.", LocalDateTime.now());

			Transaction m004 = new Transaction(CREDIT, 4125.65, "Ahorro NaranjaX.", LocalDateTime.now());
			Transaction m005 = new Transaction(DEBIT, -3325.21, "Salida del Sábado.", LocalDateTime.now());
			Transaction m006 = new Transaction(CREDIT, 29325.25, "Sueldo mes de Enero.", LocalDateTime.now());

			vin001.addTransactiosns(m001);
			vin001.addTransactiosns(m002);
			vin002.addTransactiosns(m003);

			vin003.addTransactiosns(m004);
			vin004.addTransactiosns(m005);
			vin004.addTransactiosns(m006);

			transactionRepository.save(m001);
			transactionRepository.save(m002);
			transactionRepository.save(m003);
			transactionRepository.save(m004);
			transactionRepository.save(m005);
			transactionRepository.save(m006);

			System.out.println(melba.toString());
			System.out.println(silvia.toString());
			System.out.println(m001.toString());

			Loan hipotecario = new Loan("Hipoteca", 500000.12, Set.of(12, 24 ,36, 48, 60));
			Loan personal = new Loan("Personales", 100000.65, Set.of(6, 12, 24));
			Loan automotor = new Loan("Automotrices", 300000.48, Set.of(6, 12, 24, 36));

			ClientLoan clientLoan1 = new ClientLoan("Hipotecario",400000.50, 60);
			melba.addClientLoan(clientLoan1);
			hipotecario.addClientLoan(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan("Personal", 50000.31, 12);
			melba.addClientLoan(clientLoan2);
			personal.addClientLoan(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan("Personal", 100000.21, 36);
			silvia.addClientLoan(clientLoan3);
			personal.addClientLoan(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan("Automotor", 200000.48, 36);
			silvia.addClientLoan(clientLoan4);
			automotor.addClientLoan((clientLoan4));

			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotor);

			clientRepository.save(melba);
			clientRepository.save(silvia);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(DEBIT, GOLD, "106-351-635-211", 152, LocalDate.now(), LocalDate.now().plusYears(5));
			melba.addCard(card1);

			Card card2 = new Card(CREDIT, TITANIUM, "465-893-212-987", 846, LocalDate.now(), LocalDate.now().plusYears(5));
			melba.addCard(card2);

			Card card3 = new Card(CREDIT, SILVER, "842-135-976-241", 213, LocalDate.now(), LocalDate.now().plusYears(5));
			silvia.addCard(card3);

			clientRepository.save(melba);
			clientRepository.save(silvia);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		};
	}
}
