package com.epi.worldData.Service;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Model.CountryDataCSVRepresentation;
import com.epi.worldData.util.CSVMapper;
import org.springframework.stereotype.Service;

// Mapper service, that uses the Builder and Getters from the data models to map between the CSV and database

@Service
public class CountryDataMapperService implements CSVMapper<CountryData, CountryDataCSVRepresentation> {

    @Override
    public CountryData mapTo(CountryDataCSVRepresentation k) {
        return CountryData.builder()
                .isoA2(k.getIsoA2())
                .name(k.getName())
                .continent(k.getContinent())
                .region(k.getRegion())
                .subregion(k.getSubregion())
                .type(k.getType())
                .areaKm2(k.getArea_km2())
                .population(k.getPop())
                .lifeExpectancy(k.getLifeExp())
                .gdpPerCap(k.getGdpPerCap())
                .build();
    }

    @Override
    public CountryDataCSVRepresentation unmapFrom(CountryData t) {
        return CountryDataCSVRepresentation.builder()
                .isoA2(t.getIsoA2())
                .name(t.getName())
                .continent(t.getContinent())
                .region(t.getRegion())
                .subregion(t.getSubregion())
                .type(t.getType())
                .area_km2(t.getAreaKm2())
                .pop(t.getPopulation())
                .lifeExp(t.getLifeExpectancy())
                .gdpPerCap(t.getGdpPerCap())
                .build();
    }
}
