package com.example.zboruri2.model;

import java.time.LocalDateTime;

public class Flight extends Entity<Long> {
    private String from;
    private String to;
    private LocalDateTime departureTime;
    private LocalDateTime landingTime;
    private int seats;

    public Flight() {}

    public Flight(String from, String to,
                  LocalDateTime departureTime, LocalDateTime landingTime, int seats) {
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.seats = seats;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return String.format("Flight[id=%d, from='%s', to='%s', departureTime='%s', landingTime='%s', seats='%d']"
                , id, from, to, departureTime, landingTime, seats);
    }
}

