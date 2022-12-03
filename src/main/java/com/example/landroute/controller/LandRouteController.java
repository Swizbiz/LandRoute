package com.example.landroute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LandRouteController {

    @GetMapping(value = "/routing/{origin}/{destination}")
    List<String> findLandRouteFromOriginToDestination(
            @PathVariable("origin") String origin,
            @PathVariable("destination") String destination
    );
}
