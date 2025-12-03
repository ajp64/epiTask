package com.epi.worldData;

import com.epi.worldData.Service.CountryDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorldDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldDataApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(CountryDataService countryDataService) {
		return args -> {
			countryDataService.populateCountryData();
		};
	};

}
