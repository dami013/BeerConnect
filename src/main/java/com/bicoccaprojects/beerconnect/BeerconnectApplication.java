package com.bicoccaprojects.beerconnect;


import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.service.BeerService;
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
import java.util.Scanner;


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
										ClientService clientService,
										BeerService beerService){
		return args -> {
			Scanner scanner = new Scanner(System.in);

			int choice;

			do{
				System.out.println("***********************************************");
				System.out.println("|            Welcome in BEerConnect           |");
				System.out.println("|         Here's the operation allowed        |");
				System.out.println("| - press 1 to work on a Beer                 |");
				System.out.println("| - press 2 to work on a Limited Edition Beer |");
				System.out.println("| - press 3 to work on a Pub                  |");
				System.out.println("| - press 4 to work on a Client               |");
				System.out.println("| - press 0 to exit                           |");
				System.out.println("***********************************************");
				choice = scanner.nextInt();

				switch (choice){
					case 1:
						int beerChoice;

						System.out.println("***********************************************");
						System.out.println("|    Here's the operation allowed on Beer     |");
						System.out.println("| - press 1 to create a Beer                  |");
						System.out.println("| - press 2 to read a Beer                    |");
						System.out.println("| - press 3 to update a Beer                  |");
						System.out.println("| - press 4 to delete a Beer                  |");
						System.out.println("| - press 0 to return to the menu             |");
						System.out.println("***********************************************");

						beerChoice = scanner.nextInt();

						switch (beerChoice){
							case 1:
								Pub pub = new Pub(1L);

								Beer beer = new Beer("Bicocca", pub, "IPA", "Citrusy", 5.0, "yellow", "italy", "Barley, Hops, Grapefruit Peel", 5.5f, 200);
								if(beerService.addBeer(beer)){
									System.out.println(" ---        Beer added successfully         ---");
								}
								System.out.println("***********************************************");

								continue;

							case 2:
								int searchBeer;

								System.out.println("***********************************************");
								System.out.println("|    Here's the options to search a Beer      |");
								System.out.println("| - press 1 to search all the Beer            |");
								System.out.println("| - press 2 to search a Beer by ID            |");
								System.out.println("| - press 3 to search a Beer by type          |");
								System.out.println("| - press 4 to search a Beer by name          |");
								System.out.println("| - press 0 to get back                       |");
								System.out.println("***********************************************");

								searchBeer = scanner.nextInt();
								switch (searchBeer){
									case 1:
										Iterable<Beer> beers = beerService.getAllBeers();
										for(Beer b : beers){
											System.out.println(b.toString());
										}
										System.out.println("***********************************************");
										continue;

									case 2:
										System.out.println("| Insert the ID of the desired beer         |");
										Long idBeer = scanner.nextLong();
										Beer beerSearched = beerService.getBeer(idBeer);
										System.out.println(beerSearched.toString());
										System.out.println("***********************************************");
										continue;

									case 3:
										System.out.println("| Insert the type of the desired beer       |");
										String beerType = scanner.nextLine();
										List<String> resultType = beerService.searchBeerByType(beerType);
										System.out.println(resultType.toString());
										System.out.println("***********************************************");
										continue;

									case 4:
										System.out.println("| Insert the name of the desired beer       |");
										String name = scanner.nextLine();
										List<String> resultName = beerService.searchBeerByName(name);
										System.out.println(resultName.toString());
										System.out.println("***********************************************");
										continue;

									case 0:
										continue;

									default:
										System.out.println("Invalid choice. Please try again.");
								}
								break;

							case 3:
								System.out.println("***********************************************");
								System.out.println("| Insert id beer that must be update          |");
								Long idBeer = scanner.nextLong();

								Beer beerToUpdate = beerService.getBeer(idBeer);
								Pub pubBeer = beerToUpdate.getPub();

								Beer updateBeer = new Beer(idBeer, beerToUpdate.getNameBeer(), beerToUpdate.getType(),
										beerToUpdate.getAroma(), beerToUpdate.getAlcohol(), beerToUpdate.getColor(),
										beerToUpdate.getCountry(), beerToUpdate.getIngredients(), beerToUpdate.getPrice(),
										beerToUpdate.getQuantityInStock()+100, pubBeer);
								if(beerService.updateBeer(updateBeer)){
									System.out.println(" ---       Beer updated successfully        ---");
								}
								System.out.println("***********************************************");
								continue;

							case 4:
								System.out.println("***********************************************");
								System.out.println("| Insert id beer that must be delete          |");
								Long idDeleteBeer = scanner.nextLong();

								if(beerService.deleteBeer(idDeleteBeer)){
									System.out.println(" ---       Beer deleted successfully        ---");
								}
								System.out.println("***********************************************");
								continue;

							case 0:
								continue;
						}while(beerChoice!= 0);
						break;

					case 2:
						int leChoice;

						System.out.println("********************************************************");
						System.out.println("| Here's the operation allowed on Limited Edition Beer |");
						System.out.println("| - press 1 to create a Limited Edition Beer           |");
						System.out.println("| - press 2 to read a Limited Edition Beer             |");
						System.out.println("| - press 3 to update a Limited Edition Beer           |");
						System.out.println("| - press 4 to delete a Limited Edition Beer           |");
						System.out.println("| - press 0 to get back                                |");
						System.out.println("********************************************************");

						leChoice = scanner.nextInt();

						switch (leChoice){
							case 1:
								Pub pub = new Pub(1L);

								Beer beer = new Beer("Bicocca", pub, "IPA", "Citrusy", 5.0, "yellow", "italy", "Barley, Hops, Grapefruit Peel", 5.5f, 200);
								LimitedEdition limitedBeer = new LimitedEdition("Xmas Bicocca", "IPA", "Cinnamon", 5.0, "yellow", "italy", "Barley, Hops, Grapefruit Peel, Cinnamon", 6.0f, 200, pub, "Bicocca", 2023);
								if(limitedEditionService.addLEBeer(limitedBeer)){
									System.out.println("| --- Limited Edition added successfully  --- |");
								}

								System.out.println("***********************************************");
								continue;

							case 2:
								System.out.println("***********************************************");
								System.out.println("|   Here's the options to search a LE Beer    |");
								System.out.println("| - press 1 to search all the LE Beer         |");
								System.out.println("| - press 2 to search a LE Beer by ID         |");
								System.out.println("| - press 3 to search a LE Beer by original   |");
								System.out.println("| - press 4 to search a LE Beer by name       |");
								System.out.println("| - press 0 to get back                       |");
								System.out.println("***********************************************");

								int searchLEBeer = scanner.nextInt();
								switch (searchLEBeer){
									case 1:
										Iterable<LimitedEdition> beers = limitedEditionService.getAllLEBeers();
										for(LimitedEdition b : beers){
											System.out.println(b.toString());
										}
										System.out.println("***********************************************");
										continue;

									case 2:
										System.out.println("| Insert the ID of the desired LE beer        |");
										Long idBeer = scanner.nextLong();
										LimitedEdition beerSearched = limitedEditionService.getLEBeer(idBeer);
										System.out.println(beerSearched.toString());
										System.out.println("***********************************************");
										continue;

									case 3:
										System.out.println("| Insert the original beer of the desired LE beer |");
										String originalBeer = scanner.nextLine();
										List<String> originalBeers = limitedEditionService.findLEByOriginalBeer(originalBeer);
										System.out.println(originalBeers.toString());
										System.out.println("***********************************************");
										continue;

									case 4:
										System.out.println("| Insert the name of the desired LE beer     |");
										String name = scanner.nextLine();
										List<String> resultName = limitedEditionService.searchLEByName(name);
										System.out.println(resultName.toString());
										System.out.println("***********************************************");
										continue;

									case 0:
										continue;

									default:
										System.out.println("Invalid choice. Please try again.");
								}
								break;

							case 3:
								System.out.println("***********************************************");
								System.out.println("| Insert id LE beer that must be update       |");
								Long idLEBeer = scanner.nextLong();

								LimitedEdition beerToUpdate = limitedEditionService.getLEBeer(idLEBeer);
								Pub pubBeer = beerToUpdate.getPub();

								LimitedEdition updateLE = new LimitedEdition(idLEBeer, beerToUpdate.getNameBeer(), beerToUpdate.getType(),
										beerToUpdate.getAroma(), beerToUpdate.getAlcohol(), beerToUpdate.getColor(),
										beerToUpdate.getCountry(), beerToUpdate.getIngredients(), beerToUpdate.getPrice(),
										beerToUpdate.getQuantityInStock()+100, pubBeer, beerToUpdate.getOriginalBeer(), beerToUpdate.getProductionYear());
								if(limitedEditionService.updateLEBeer(updateLE)){
									System.out.println(" ---      LE Beer updated successfully      ---");
								}
								System.out.println("***********************************************");
								continue;

							case 4:
								System.out.println("***********************************************");
								System.out.println("| Insert id LE beer that must be delete        |");
								Long idDeleteBeer = scanner.nextLong();

								if(limitedEditionService.deleteLEBeer(idDeleteBeer)){
									System.out.println("| --- Limited Edition deleted successfully --- |");
								}
								System.out.println("***********************************************");
								break;

							case 0:
								break;

							default:
								System.out.println("Invalid choice. Please try again.");
						}while(leChoice!= 0);
						break;

					case 3:
						int pubChoice;

						System.out.println("***********************************************");
						System.out.println("|    Here's the operation allowed on Pub      |");
						System.out.println("| - press 1 to create a Pub                   |");
						System.out.println("| - press 2 to read a Pub                     |");
						System.out.println("| - press 3 to update a Pub                   |");
						System.out.println("| - press 4 to delete a Pub                   |");
						System.out.println("| - press 0 to exit                           |");
						System.out.println("***********************************************");

						pubChoice = scanner.nextInt();

						switch (pubChoice){
							case 1:
								break;

							case 2:
								break;

							case 3:
								break;

							case 4:
								break;

							case 0:
								break;

							default:
								System.out.println("Invalid choice. Please try again.");

						}while(pubChoice!= 0);
						break;

					case 4:
						int clientChoice;

						System.out.println("***********************************************");
						System.out.println("|    Here's the operation allowed on Client   |");
						System.out.println("| - press 1 to create a Client                |");
						System.out.println("| - press 2 to read a Client                  |");
						System.out.println("| - press 3 to update a Client                |");
						System.out.println("| - press 4 to delete a Client                |");
						System.out.println("| - press 0 to exit                           |");
						System.out.println("***********************************************");

						clientChoice = scanner.nextInt();

						switch (clientChoice){
							case 1:
								break;

							case 2:
								break;

							case 3:
								break;

							case 4:
								break;

							case 0:
								break;

						}while(clientChoice!= 0);
						break;

					case 0:
						System.out.println("|                See you soon :)              |");
						System.out.println("***********************************************");

				}

			}while (choice != 0);
			scanner.close();

			/*
			List<String> listUser = clientReviewService.findReviewsByBeerCountryAndRating("Germany", 4);
			System.out.println(listUser);

			List<String> listBeer = pubService.findBeerByPub(1L);
			System.out.println(listBeer);

			List<String> listLE = limitedEditionService.findLEByOriginalBeer("Beer1");
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
			 */

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
