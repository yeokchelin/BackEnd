package com.example.StationMisyullaeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StationMisyullaengApplication {

	public static void main(String[] args) {
		SpringApplication.run(StationMisyullaengApplication.class, args);
	}

}
