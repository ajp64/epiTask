package com.epi.worldData.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.RequiredArgsConstructor;
import com.epi.worldData.util.CSVMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;

/*
 Method for parsing CSV. Creates mapping strategy from the provided class, creates instance of CsvToBeanBuilder
 and sets rules to ignore empty lines and leading spaces. Data is then parsed into a set of T
*/

@Service
@RequiredArgsConstructor
public class CsvParsingService<T, K> {

    final private CSVMapper<T, K> csvMapper;

    final public Set<T> parseCSV(InputStream stream , Class<K> clazz) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(stream))) {
            HeaderColumnNameMappingStrategy<K> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            CsvToBean<K> csvToBean = new CsvToBeanBuilder<K>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse()
                    .stream()
                    .map(csvMapper::mapTo)
                    .collect(Collectors.toSet());
        }
    }

}
