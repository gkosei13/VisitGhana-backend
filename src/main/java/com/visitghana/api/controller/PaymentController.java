package com.visitghana.api.controller;

import com.visitghana.api.dto.TicketRequest;
import com.visitghana.api.dto.VerifyRequest;
import com.visitghana.api.model.Ticket;
import com.visitghana.api.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private TicketRepository ticketRepository;

    @Value("${paystack.secret.key}")
    private String paystackSecretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // STEP 1 — Initialize a payment
    @PostMapping("/initialize")
    public ResponseEntity<?> initializePayment(
            @RequestBody TicketRequest request) {

        // Paystack expects amount in pesewas (multiply by 100)
        int amountInPesewas =
                request.getTotalAmount()
                        .multiply(BigDecimal.valueOf(100))
                        .intValue();

        // Build request to Paystack
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("email", request.getEmail());
        body.put("amount", amountInPesewas);
        body.put("currency", "GHS");

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        try {
            // Call Paystack
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://api.paystack.co/transaction/initialize",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> data =
                    (Map<String, Object>) responseBody.get("data");

            String reference = (String) data.get("reference");
            String authUrl =
                    (String) data.get("authorization_url");

            // Save a pending ticket
            Ticket ticket = new Ticket();
            ticket.setUserId(request.getUserId());
            ticket.setSiteId(request.getSiteId());
            ticket.setSiteName(request.getSiteName());
            ticket.setAdults(request.getAdults());
            ticket.setChildren(request.getChildren());
            ticket.setTotalAmount(request.getTotalAmount());
            ticket.setPaymentReference(reference);
            ticket.setPaymentStatus("pending");
            ticketRepository.save(ticket);

            // Send the payment URL back to the app
            Map<String, String> result = new HashMap<>();
            result.put("authorization_url", authUrl);
            result.put("reference", reference);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Payment initialization failed: "
                            + e.getMessage());
        }
    }

    // STEP 2 — Verify a payment
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestBody VerifyRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://api.paystack.co/transaction/verify/"
                            + request.getReference(),
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> data =
                    (Map<String, Object>) responseBody.get("data");

            String status = (String) data.get("status");

            // Find the ticket
            Ticket ticket = ticketRepository
                    .findByPaymentReference(request.getReference());

            if (ticket == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Ticket not found");
            }

            if ("success".equals(status)) {
                // Payment succeeded — confirm the ticket
                ticket.setPaymentStatus("paid");
                ticket.setTicketCode(
                        "VG-" + UUID.randomUUID()
                                .toString().substring(0, 8)
                                .toUpperCase());
                ticketRepository.save(ticket);

                return ResponseEntity.ok(ticket);
            } else {
                ticket.setPaymentStatus("failed");
                ticketRepository.save(ticket);
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Payment was not successful");
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Verification failed: " + e.getMessage());
        }
    }

    // Get all tickets for a user
    @GetMapping("/tickets/{userId}")
    public List<Ticket> getUserTickets(
            @PathVariable Long userId) {
        return ticketRepository.findByUserId(userId);
    }
}