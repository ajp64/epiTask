package com.epi.worldData.Repository;

import com.epi.worldData.Model.CountryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryData, String> {
}
