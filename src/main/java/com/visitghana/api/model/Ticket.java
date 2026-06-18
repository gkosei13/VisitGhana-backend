package com.visitghana.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    private Long userId;
    private Long siteId;
    private String siteName;
    private Integer adults;
    private Integer children;
    private BigDecimal totalAmount;
    private String paymentReference;
    private String paymentStatus = "pending";
    private String ticketCode;

    private LocalDateTime purchaseDate;

    @PrePersist
    protected void onCreate() {
        purchaseDate = LocalDateTime.now();
    }
}