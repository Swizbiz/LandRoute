package com.example.landroute.data;

import com.example.landroute.entity.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CountriesData {
    private final ObjectMapper objectMapper;
    private final TypeReference<List<Country>> reference = new TypeReference<List<Country>>() {
    };
    private Map<String, Country> countries;


    @Autowired
    public CountriesData(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void initData() throws Exception {
        try {
            File file = ResourceUtils.getFile("classpath:countries.json");
            countries = objectMapper.readValue(file, reference)
                    .stream()
                    .collect(Collectors.toMap(Country::getName, country -> country));
        } catch (IOException e) {
            throw new Exception("Cannot load countries from file: classpath:countries.json");
        }
    }

    public Map<String, Country> getCountries() {
        return countries;
    }

}
