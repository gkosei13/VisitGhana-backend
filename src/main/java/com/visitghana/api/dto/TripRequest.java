package com.visitghana.api.dto;

import lombok.Data;

@Data
public class TripRequest {
    private Long userId;
    private Long siteId;
    private String siteName;
    private String siteRegion;
    private String siteColor;
    private String visitDate;
}