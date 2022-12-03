package com.example.landroute.service.impl;

import com.example.landroute.data.CountriesData;
import com.example.landroute.exception.RouteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LandRouteServiceImplTest {

    @Autowired
    private CountriesData countriesData;

    @Autowired
    private LandRouteServiceImpl landRouteService;

    @Test
    void findLandRoute_Success() {
        assertEquals(List.of("CZE", "AUT", "ITA"), landRouteService.findRoute("CZE", "ITA"));
    }

    @Test
    void findLandRoute_CannotReach() {
        RouteException routeException = assertThrows(RouteException.class, () -> landRouteService.findRoute("CZE", "USA"));
        assertEquals("The route from CZE to USA cannot be reached by land.", routeException.getMessage());
    }

    @Test
    void findLandRoute_NonExistCountry() {
        RouteException exception = assertThrows(RouteException.class, () -> landRouteService.findRoute("OOO", "ITA"));
        assertEquals("Unknown country: OOO", exception.getMessage());
        RouteException routeException = assertThrows(RouteException.class, () -> landRouteService.findRoute("CZE", "DDD"));
        assertEquals("Unknown country: DDD", routeException.getMessage());
    }

}