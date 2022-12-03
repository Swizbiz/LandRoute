package com.example.landroute.controller.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LandRouteControllerImplTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findLandRouteFromOriginToDestination_Success() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("CZE", "ITA"), String.class);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody(), "[\"CZE\",\"AUT\",\"ITA\"]");
    }

    @Test
    void findLandRouteFromOriginToDestination_RouteNotFound() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("CZE", "USA"), String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @CsvSource({
            "OOO, ITA",
            "CZE, DDD",
            "OOO, DDD"
    })
    void findLandRouteFromOriginToDestination_RouteNotFound(final String origin, final String destination) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(origin, destination), String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private String getUrl(final String origin, final String destination) {
        return String.format("http://localhost:%d/routing/%s/%s", port, origin, destination);
    }

}