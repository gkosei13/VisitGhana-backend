package com.visitghana.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "site_photos")
public class SitePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    private Long siteId;
    private String imageUrl;
    private Integer displayOrder;
}