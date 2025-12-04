package com.epi.worldData.Controller;

import com.epi.worldData.Service.DataAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DataAnalysisController {

    private final DataAnalysisService dataAnalysisService;

    public DataAnalysisController(DataAnalysisService service) { this.dataAnalysisService = service;}

    @GetMapping("/continentWithMostCountries")
    public ResponseEntity<String> getContinentWithMostCountries() {
        String continent = dataAnalysisService.findContinentWithMostCountries();
        return ResponseEntity.ok(continent);
    }

}
