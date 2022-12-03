package com.example.landroute.service;

import java.util.List;

public interface LandRouteService {
    List<String> findRoute(String origin, String destination);
}
