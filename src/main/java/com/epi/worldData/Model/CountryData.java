package com.epi.worldData.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryData {
    @Id
    private String isoA2;
    private String name;
    private String continent;
    private String region;
    private String subregion;
    private String type;
    private Double areaKm2;
    private Double population;
    private Double lifeExpectancy;
    private Double gdpPerCap;
}
