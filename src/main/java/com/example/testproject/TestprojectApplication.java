package com.example.testproject;

import com.example.testproject.entity.Beer;
import com.example.testproject.entity.Pub;
import com.example.testproject.repository.BeerRepository;
import com.example.testproject.repository.PubRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.example.testproject")
public class TestprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestprojectApplication.class, args);
	}

	// @Bean
	// CommandLineRunner commandLineRunner(PubRepository pubRepository){
		// return args -> {
			// Pub pub = new Pub("full","cassano magnago",1989);
		//pubRepository.save(pub);
		//};
	//}
	/*
	@Bean
	CommandLineRunner commandLineRunner(BeerRepository beerRepository){
		return args -> {
			Beer beer = new Beer(
					"slalom",
					"IPA",
					"strong",
					9.9,
					"yellow",
					null,
					"Italy",
					"Acqua",
					"Very strong beer",
					6.0,
					100
			);
			beerRepository.save(beer);
		};
	}
	*/
}
