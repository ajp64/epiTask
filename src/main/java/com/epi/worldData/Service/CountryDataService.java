package com.epi.worldData.Service;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Model.CountryDataCSVRepresentation;
import com.epi.worldData.Repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

// Data service, providing methods to populate the database by parsing a CSV file

@Service
@RequiredArgsConstructor
public class CountryDataService {

    final CsvService<CountryData, CountryDataCSVRepresentation> csvService;
    final CountryRepository countryRepository;
    final FileStorageService fileStorageService;

    public Integer saveFromCsv(InputStream inputStream) throws IOException {
        Set<CountryData> countries;
        countries = parseCSV(inputStream);
        countryRepository.deleteAll();
        countryRepository.saveAll(countries);
        return countries.size();
    }

    private Set<CountryData> parseCSV(InputStream inputStream) throws IOException {
        return csvService.parseCSV(inputStream,CountryDataCSVRepresentation.class);
    }

    public void populateCountryData() throws Exception {
        Resource resource = fileStorageService.loadAsResource("C:\\dev\\worldData\\src\\main\\resources\\csv\\worldData.csv");
        try (InputStream inputStream = resource.getInputStream()) {
            saveFromCsv(inputStream);
        }
    }
}
