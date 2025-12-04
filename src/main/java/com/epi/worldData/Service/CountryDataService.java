package com.epi.worldData.Service;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Model.CountryDataCSVRepresentation;
import com.epi.worldData.Repository.CountryRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

// Data service, providing methods to populate the database by parsing a CSV file

@Service
@RequiredArgsConstructor
public class CountryDataService {

    final CsvParsingService<CountryData, CountryDataCSVRepresentation> csvParsingService;
    final CountryRepository countryRepository;
    final CsvLoadingService csvLoadingService;

    public Integer saveFromCsv(InputStream inputStream) throws IOException {
        Set<CountryData> countries;
        countries = parseCSV(inputStream);
        countryRepository.deleteAll();
        countryRepository.saveAll(countries);
        return countries.size();
    }

    private Set<CountryData> parseCSV(InputStream inputStream) throws IOException {
        return csvParsingService.parseCSV(inputStream,CountryDataCSVRepresentation.class);
    }

    public void populateCountryData() throws Exception {
        Resource resource = csvLoadingService.loadAsResource("csv/worldData.csv");
        try (InputStream inputStream = resource.getInputStream()) {
            saveFromCsv(inputStream);
            writeCsvFromBean();
        }
    }

    public String writeCsvFromBean() throws Exception {

        String tmp = System.getProperty("java.io.tmpdir");
        Path path = Paths.get(tmp, "output.csv");
        System.out.println("CSV exported to: " + path.toAbsolutePath());

        List<CountryData> sampleData = countryRepository.findAll();

        try (Writer writer  = new FileWriter(path.toString())) {

            StatefulBeanToCsv<CountryData> sbc = new StatefulBeanToCsvBuilder<CountryData>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(sampleData);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Files.readString(Path.of(path.toString()));
    }
}
