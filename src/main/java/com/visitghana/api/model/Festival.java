package com.visitghana.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "festivals")
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long festivalId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;
    private String region;
    private String category;
    private String startDate;
    private String endDate;
    private String duration;
    private String month;
    private String day;
    private String color;

    @Column(columnDefinition = "TEXT")
    private String highlights;

    @Column(columnDefinition = "TEXT")
    private String tip;

    private String imageUrl;
}