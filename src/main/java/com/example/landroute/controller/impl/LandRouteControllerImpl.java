package com.example.landroute.controller.impl;

import com.example.landroute.controller.LandRouteController;
import com.example.landroute.exception.RouteException;
import com.example.landroute.service.LandRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LandRouteControllerImpl implements LandRouteController {

    private final LandRouteService landRouteService;

    @Autowired
    public LandRouteControllerImpl(LandRouteService landRouteService) {
        this.landRouteService = landRouteService;
    }

    @Override
    public List<String> findLandRouteFromOriginToDestination(String origin, String destination) {
        try {
            return landRouteService.findRoute(origin, destination);
        }
        catch (RouteException re) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, re.getMessage(), re);
        }
    }

}
