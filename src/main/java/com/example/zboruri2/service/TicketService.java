package com.example.zboruri2.service;

import com.example.zboruri2.model.Ticket;
import com.example.zboruri2.repository.TicketDBRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class TicketService {
    private TicketDBRepository ticketDBRepository;

    public TicketService(TicketDBRepository ticketDBRepository) {
        this.ticketDBRepository = ticketDBRepository;
    }

    public List<Ticket> findUserTickets(String usernameInput) {
        return ticketDBRepository.findUserTickets(usernameInput);
    }

    public List<Ticket> findDateTickets(Timestamp purchaseTimeInput) {
        return ticketDBRepository.findDateTickets(purchaseTimeInput);
    }

    public Optional<Ticket> save(Ticket ticket) {
        return ticketDBRepository.save(ticket);
    }

    public Long getLastId() {
        return ticketDBRepository.getLastId();
    }

}
