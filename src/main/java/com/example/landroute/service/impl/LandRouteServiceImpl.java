package com.example.landroute.service.impl;

import com.example.landroute.data.CountriesData;
import com.example.landroute.entity.Country;
import com.example.landroute.exception.RouteException;
import com.example.landroute.service.LandRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Stream;

@Service
public class LandRouteServiceImpl implements LandRouteService {

    private final CountriesData countriesData;

    @Autowired
    public LandRouteServiceImpl(final CountriesData countriesData) {
        this.countriesData = countriesData;
    }

    @Override
    public List<String> findRoute(String origin, String destination) {
        checkCountryExist(origin);
        checkCountryExist(destination);
        if (origin.equals(destination)) {
            return List.of(origin);
        }

        Map<String, Country> countries = countriesData.getCountries();
        List<String> route = findRoute(countries, countries.get(origin), countries.get(destination));
        if (route.isEmpty()) {
            throw new RouteException(String.format("The route from %s to %s cannot be reached by land.", origin, destination));
        }
        return route;
    }

    private void checkCountryExist(String country) {
        if (countriesData.getCountries().get(country) == null) {
            throw new RouteException("Unknown country: " + country);
        }
    }

    private List<String> findRoute(Map<String, Country> countries, Country origin, Country destination) {
        final List<String> route = new LinkedList<>();
        final Map<Country, Boolean> visited = new HashMap<>();
        final Map<Country, Country> path = new HashMap<>();
        final Queue<Country> routeQueue = new ArrayDeque<>();
        Country currentCountry = origin;
        routeQueue.add(currentCountry);
        visited.put(currentCountry, true);
        while (!routeQueue.isEmpty()) {
            currentCountry = routeQueue.poll();
            for (String adjacent : currentCountry.getBorders()) {
                Country adjacentCountry = countries.get(adjacent);
                if (!visited.containsKey(adjacentCountry)) {
                    visited.put(adjacentCountry, true);
                    routeQueue.add(adjacentCountry);
                    path.put(adjacentCountry, currentCountry);
                    if (destination.equals(adjacentCountry)) {
                        Stream.iterate(destination, Objects::nonNull, path::get)
                                .forEach(country -> route.add(0, country.getName()));
                        return route;
                    }
                }
            }
        }
        return route;
    }
}
