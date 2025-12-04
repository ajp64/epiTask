package com.epi.worldData.Controller;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Service.DataAnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
This controller uses the /countryData endpoint to return an HTML template, which is populated with data
from the dataAnalysisService. Request parameters from the UI are used for filtering.
 */
@Controller
public class DataAnalysisController {

    private final DataAnalysisService dataAnalysisService;

    public DataAnalysisController(DataAnalysisService dataAnalysisService) {this.dataAnalysisService = dataAnalysisService;}

    @GetMapping(value = "/countryData")
    public String countryData(
            Model model,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String subregion,
            @RequestParam(required = false) String type
    ) {

        List<CountryData> dataPage = dataAnalysisService.findAll();

        List<CountryData> filteredList = dataPage.stream()
                        .filter(p -> region == null || p.getRegion().contains(region))
                        .filter(p -> continent == null || p.getContinent().contains(continent))
                        .filter(p -> subregion == null || p.getSubregion().contains(subregion))
                        .filter(p -> type == null || p.getType().contains(type))
                        .collect(Collectors.toList());

        double areaSum = sumDouble(filteredList, CountryData::getAreaKm2);
        double popSum  = sumDouble(filteredList, CountryData::getPopulation);
        double gdpAv  = averageDouble(filteredList, CountryData::getGdpPerCap);
        double lifeExpAv  = averageDouble(filteredList, CountryData::getLifeExpectancy);

        model.addAttribute("dataPage", filteredList);
        model.addAttribute("areaSum", areaSum);
        model.addAttribute("popSum", popSum);
        model.addAttribute("gdpAv", gdpAv);
        model.addAttribute("lifeExpAv", lifeExpAv);

        return "dataPage";
    }

    private <T> double sumDouble(List<T> list, Function<T, Double> getter) {
        return list.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private <T> double averageDouble(List<T> list, Function<T, Double> getter) {
        DoubleSummaryStatistics stats = list.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        return stats.getAverage();
    }

}
