package com.mindhub.homebanking;

import com.mindhub.homebanking.modelos.Account;
import com.mindhub.homebanking.repositorios.AccountRepository;
import com.mindhub.homebanking.repositorios.ClientRepository;
import com.mindhub.homebanking.modelos.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
		return args -> {
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			Client silvia = new Client("Silvia", "Montiel", "silvia@outlook.es");

			Account vin001 = new Account("VIN001", LocalDate.now(), 5000);
			Account vin002 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Account vin003 = new Account("VIN003", LocalDate.now(), 4500);
			Account vin004 = new Account("VIN004", LocalDate.now().plusDays(1), 8000);

			melba.addAccounts(vin001);
			melba.addAccounts(vin002);
			silvia.addAccounts(vin003);
			silvia.addAccounts(vin004);

			clientRepository.save(melba);
			clientRepository.save(silvia);

			accountRepository.save(vin001);
			accountRepository.save(vin002);

			accountRepository.save(vin003);
			accountRepository.save(vin004);

		};
	}
}
