package com.epi.worldData.Repository;

import com.epi.worldData.Model.CountryData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<CountryData, String> {

    @Query(
            value = "SELECT continent FROM COUNTRY_DATA GROUP BY continent ORDER BY COUNT(*) DESC LIMIT 1",
            nativeQuery = true
    )
    String findContinentWithMostCountries();

    @Query(value = "SELECT DISTINCT region FROM COUNTRY_DATA", nativeQuery = true)
    List<String> findDistinctByRegion();

    @Query(value = "SELECT SUM(area_km2) FROM COUNTRY_DATA WHERE region = ?1", nativeQuery = true)
    Double sumAreaByRegion(String region);

}
