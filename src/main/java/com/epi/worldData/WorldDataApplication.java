package com.epi.worldData;

import com.epi.worldData.Service.CountryDataService;
import com.epi.worldData.Service.DataAnalysisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class WorldDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldDataApplication.class, args);
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner runner(CountryDataService countryDataService, DataAnalysisService dataAnalysisService) {
		return args -> {
			countryDataService.populateCountryData();
		};
	};

}
