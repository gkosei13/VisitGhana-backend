package com.visitghana.api.repository;

import com.visitghana.api.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository
        extends JpaRepository<Restaurant, Long> {

    // Get restaurants near a specific site
    List<Restaurant> findBySiteId(Long siteId);
}