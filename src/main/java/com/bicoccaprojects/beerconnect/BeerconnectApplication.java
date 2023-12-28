package com.bicoccaprojects.beerconnect;


import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.util.Optional;


@SpringBootApplication
@EntityScan(basePackages = "com.bicoccaprojects.beerconnect")
public class BeerconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerconnectApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ClientService clientService) {
		return args -> {
			Optional<Client> customer = clientService.getClient(1L);
			Optional<Client> customerFriend = clientService.getClient(2L);

			if (customer.isPresent() && customerFriend.isPresent()) {
				Client client = customer.get();
				Client friend = customerFriend.get();

				// Aggiungi la relazione di follow
				clientService.followedByClient(client, friend);

				// Stampa tutti i seguiti del cliente
				clientService.printFollowedByClient(client);
			} else {
				System.out.println("errore");
			}
		};
	}
}