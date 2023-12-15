package com.bicoccaprojects.beerconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.bicoccaprojects.beerconnect")
public class BeerconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerconnectApplication.class, args);
	}
	/*
	@Bean
	CommandLineRunner commandLineRunner(PubRepository pubRepository){
		return args -> {
			Pub pub = new Pub("full","cassano magnago",1989);
			pubRepository.save(pub);
		};
	}
	*/
	/*
	@Bean
	CommandLineRunner commandLineRunner(BeerRepository beerRepository){
		return args -> {
			Beer beer = new Beer(
					"slalom",
					"ipa",
					"strong",
					9.9,
					"yellow",
					"italy",
					"water",
					5F,
					100
					);
			beerRepository.save(beer);
		};
	}
	*/



}
