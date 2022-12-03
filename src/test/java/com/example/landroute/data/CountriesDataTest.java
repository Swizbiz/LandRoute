package com.example.landroute.data;

import com.example.landroute.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CountriesDataTest {

    @Autowired
    private CountriesData countriesData;

    @Test
    void loadDataFromFile() {
        final Map<String, Country> countries = this.countriesData.getCountries();
        assertEquals(250, countries.size());
        assertEquals(Collections.EMPTY_LIST, countries.get("ABW").getBorders());
        assertEquals(List.of("IRN", "PAK", "TKM", "UZB", "TJK", "CHN"), countries.get("AFG").getBorders());
        assertEquals(5, countries.get("KAZ").getBorders().size());
    }
}