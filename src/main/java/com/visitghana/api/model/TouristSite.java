package com.visitghana.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tourist_sites")
public class TouristSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long siteId;

    @Column(nullable = false)
    private String name;

    private String region;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String historyText;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal rating;
    private Integer reviewCount;
    private String color;
    private Boolean isVerified;
    private String imageUrl;
    private String history;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}