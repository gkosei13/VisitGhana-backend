package com.visitghana.api.repository;

import com.visitghana.api.model.PlannedTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlannedTripRepository
        extends JpaRepository<PlannedTrip, Long> {

    List<PlannedTrip> findByUserId(Long userId);
}