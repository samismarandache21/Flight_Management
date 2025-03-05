package com.example.zboruri2.service;

import com.example.zboruri2.model.Flight;
import com.example.zboruri2.repository.FlightDBRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class FlightService {

    private FlightDBRepository flightDBRepository;

    public FlightService(FlightDBRepository flightDBRepository) {
        this.flightDBRepository = flightDBRepository;
    }

    public List<String> findLocations() {
        return flightDBRepository.findLocations();
    }

    public List<Flight> findDayFlights(LocalDateTime dateInput) {
        return flightDBRepository.getDayFlights(dateInput);
    }

    public List<Flight> findFilteredFlights(String from, String to, Timestamp startOfDay, Timestamp endOfDay) {
        return flightDBRepository.findFilteredFlights(
                from,
                to,
                startOfDay, endOfDay);
    }
}
