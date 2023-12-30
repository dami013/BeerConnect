package com.bicoccaprojects.beerconnect;


import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.service.ClientService;
import com.bicoccaprojects.beerconnect.service.LimitedEditionService;
import com.bicoccaprojects.beerconnect.service.PubService;
import com.bicoccaprojects.beerconnect.service.relational_service.ClientReviewService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;


@SpringBootApplication
@EntityScan(basePackages = "com.bicoccaprojects.beerconnect")
public class BeerconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerconnectApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ClientReviewService clientReviewService,
										PubService pubService,
										LimitedEditionService limitedEditionService,
										ClientService clientService){
		return args -> {
			List<String> listUser = clientReviewService.clientList("Germany", 4);
			System.out.println(listUser);

			List<String> listBeer = pubService.findBeerByPub(1L);
			System.out.println(listBeer);

			List<String> listLE = limitedEditionService.findLEBeer("Beer1");
			System.out.println(listLE);

			List<String> listFollowers = clientService.getFollowersPreferences(1L);
			System.out.println("1L è seguito da: " + listFollowers);

			List<String> listFollowed = clientService.getFollowedPreferences(1L);
			if(listFollowed.isEmpty()){
				System.out.println("1L non è seguito da nessuno");
			}else {
				System.out.println("1L segue: " + listFollowed);
			}

			Optional<Client> customer = clientService.getClient(1L);
			Optional<Client> customerFriend = clientService.getClient(2L);

			if (customer.isPresent() && customerFriend.isPresent()) {
				Client client = customer.get();
				Client friend = customerFriend.get();

				// Aggiungi la relazione di follow
				clientService.unfollowClient(client, friend);

				// Stampa tutti i seguiti del cliente
				clientService.printFollowedByClient(client);
			} else {
				System.out.println("errore");
			}

		};
	}

	/*@Bean
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
	}*/
}