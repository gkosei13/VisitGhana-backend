package com.visitghana.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "festival_photos")
public class FestivalPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    private Long festivalId;
    private String imageUrl;
    private Integer displayOrder;
}