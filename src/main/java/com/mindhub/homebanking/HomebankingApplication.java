package com.mindhub.homebanking;

import com.mindhub.homebanking.repositorios.ClienteRepositorio;
import com.mindhub.homebanking.modelos.Cliente;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClienteRepositorio clienteRepositorio) {
		return args -> {
			Cliente melba = new Cliente("Melba", "Morel", "melba@mindhub.com");
			Cliente silvia = new Cliente("Silvia", "Montiel", "silvia@outlook.es");

			clienteRepositorio.save(melba);
			clienteRepositorio.save(silvia);
		};
	}
}
