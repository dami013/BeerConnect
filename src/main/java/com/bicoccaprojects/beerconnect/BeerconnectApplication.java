package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import com.bicoccaprojects.beerconnect.service.ClientService;
import com.bicoccaprojects.beerconnect.service.relational_service.ClientReviewService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.bicoccaprojects.beerconnect")
public class BeerconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerconnectApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ClientReviewService clientReviewService, ClientService clientService){
		return args -> {
			Client client = new Client(
					"damiano",
					"damianoficaxdxd@gmail.com",
					2004,
					"canzo",
					"qualunque");

			Beer beer = new Beer(
					"xmas",
					"lager",
					"strong",
					6.5,
					"green",
					"ireland",
					"luppolo",
					5.0f,
					100
			);

			ClientReview clientReview = new ClientReview(client, beer,4, "Birra molto buona");
			clientReviewService.addReview(clientReview);
		};
	}

}
