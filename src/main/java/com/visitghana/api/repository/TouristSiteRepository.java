package com.visitghana.api.repository;

import com.visitghana.api.model.TouristSite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TouristSiteRepository
        extends JpaRepository<TouristSite, Long> {

    List<TouristSite> findByCategory(String category);

    List<TouristSite> findByRegion(String region);
}