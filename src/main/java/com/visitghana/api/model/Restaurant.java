package com.visitghana.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private Long siteId;
    private String name;
    private String type;
    private String cuisine;
    private Double latitude;
    private Double longitude;
    private String distance;
    private Double rating;
    private String priceRange;
    private String phone;
    private String color;
}