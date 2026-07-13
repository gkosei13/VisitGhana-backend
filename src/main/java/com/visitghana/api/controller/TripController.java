package com.visitghana.api.controller;

import com.visitghana.api.dto.TripRequest;
import com.visitghana.api.model.PlannedTrip;
import com.visitghana.api.repository.PlannedTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripController {

    @Autowired
    private PlannedTripRepository tripRepository;

    // Save a planned trip
    @PostMapping("/add")
    public ResponseEntity<?> addTrip(
            @RequestBody TripRequest request) {

        PlannedTrip trip = new PlannedTrip();
        trip.setUserId(request.getUserId());
        trip.setSiteId(request.getSiteId());
        trip.setSiteName(request.getSiteName());
        trip.setSiteRegion(request.getSiteRegion());
        trip.setSiteColor(request.getSiteColor());
        trip.setVisitDate(request.getVisitDate());
        trip.setStatus("planned");

        PlannedTrip saved = tripRepository.save(trip);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // Get all planned trips for a user
    @GetMapping("/{userId}")
    public List<PlannedTrip> getUserTrips(
            @PathVariable Long userId) {
        return tripRepository.findByUserId(userId);
    }

    // Delete a planned trip
    @DeleteMapping("/{tripId}")
    public ResponseEntity<?> deleteTrip(
            @PathVariable Long tripId) {
        tripRepository.deleteById(tripId);
        return ResponseEntity.ok("Trip removed");
    }
}