package com.visitghana.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "planned_trips")
public class PlannedTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    private Long userId;
    private Long siteId;
    private String siteName;
    private String siteRegion;
    private String siteColor;
    private String visitDate;
    private String status = "planned";

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}