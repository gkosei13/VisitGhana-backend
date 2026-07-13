package com.visitghana.api.controller;

import com.visitghana.api.model.Restaurant;
import com.visitghana.api.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Get ALL restaurants (for the map)
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // Get restaurants near a specific site (for site detail page)
    @GetMapping("/site/{siteId}")
    public List<Restaurant> getRestaurantsBySite(
            @PathVariable Long siteId) {
        return restaurantRepository.findBySiteId(siteId);
    }
}