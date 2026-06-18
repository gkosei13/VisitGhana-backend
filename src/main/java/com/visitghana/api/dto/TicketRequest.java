package com.visitghana.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TicketRequest {
    private Long userId;
    private Long siteId;
    private String siteName;
    private Integer adults;
    private Integer children;
    private BigDecimal totalAmount;
    private String email;
}