package com.visitghana.api.controller;

import com.visitghana.api.model.TouristSite;
import com.visitghana.api.repository.TouristSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
@CrossOrigin(origins = "*")
public class TouristSiteController {

    @Autowired
    private TouristSiteRepository siteRepository;

    // GET all sites
    @GetMapping
    public List<TouristSite> getAllSites() {
        return siteRepository.findAll();
    }

    // GET one site by ID
    @GetMapping("/{id}")
    public TouristSite getSiteById(@PathVariable Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Site not found"));
    }

    // GET sites by category
    @GetMapping("/category/{category}")
    public List<TouristSite> getSitesByCategory(
            @PathVariable String category) {
        return siteRepository.findByCategory(category);
    }

    // GET sites by region
    @GetMapping("/region/{region}")
    public List<TouristSite> getSitesByRegion(
            @PathVariable String region) {
        return siteRepository.findByRegion(region);
    }
}