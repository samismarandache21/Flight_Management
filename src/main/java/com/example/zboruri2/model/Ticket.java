package com.example.zboruri2.model;

import java.time.LocalDateTime;

public class Ticket extends Entity<Long> {
    private String username;
    private Long flightId;
    private LocalDateTime purchaseTime;

    public Ticket() {}

    public Ticket(String username, Long flightId, LocalDateTime purchaseTime) {
        this.username = username;
        this.flightId = flightId;
        this.purchaseTime = purchaseTime;
    }

    public String getUsername() {
        return username;
    }

    public Long getFlightId() {
        return flightId;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    @Override
    public String toString() {
        return String.format("Ticket[id=%d, username='%s', flightId='%d', purchaseTime='%s']",
                id, username, flightId, purchaseTime);
    }
}