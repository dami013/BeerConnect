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
}
