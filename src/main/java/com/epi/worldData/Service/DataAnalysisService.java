package com.epi.worldData.Service;

import com.epi.worldData.Repository.CountryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataAnalysisService {

    private final CountryRepository repository;

    public DataAnalysisService(CountryRepository repository) { this.repository = repository;}

    public String findContinentWithMostCountries() {
        return repository.findContinentWithMostCountries();
    }

    public List<String> findListOfRegions() {
        return repository.findDistinctByRegion();
    }

    public String findRegionWithGreatestArea() {
        Map<String, Double> regionAreas = new HashMap<>();
        List<String> allRegions = repository.findDistinctByRegion();
        allRegions.forEach(region -> {
            Double regionArea = repository.sumAreaByRegion(region);
            regionAreas.put(region, regionArea);
        });

        return regionAreas.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No value found");
    }
}
