package com.visitghana.api.repository;

import com.visitghana.api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUserId(Long userId);

    Ticket findByPaymentReference(String paymentReference);
}