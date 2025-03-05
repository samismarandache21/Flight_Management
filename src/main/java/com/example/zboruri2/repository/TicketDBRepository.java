package com.example.zboruri2.repository;

import com.example.zboruri2.model.Client;
import com.example.zboruri2.model.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDBRepository extends DBRepository<Long, Ticket> {
    public TicketDBRepository(String connectionString, String user, String password) {
        super(connectionString, user, password);
    }

    @Override
    protected void refreshLastId() {
        try {
            Connection connection = DriverManager.getConnection(connectionString, user, password);
            String lastID_query = "SELECT setval(pg_get_serial_sequence('tickets', 'id'), (SELECT MAX(id) FROM tickets))";
            ResultSet res = connection.prepareStatement(lastID_query).executeQuery();
            if (res.next()) {
                lastId = res.getLong("setval");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read() {
        try (Connection connection = DriverManager.getConnection(connectionString, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM tickets")) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Long flightId = resultSet.getLong("flight_id");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchase_time").toLocalDateTime();
                Ticket temp = new Ticket(username, flightId, purchaseTime);
                temp.setId(id);
                super.save(temp);
            }

            refreshLastId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> findUserTickets(String usernameInput) {
        List<Ticket> userTickets = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tickets WHERE username = ?");
            statement.setString(1, usernameInput);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Long flightId = resultSet.getLong("flight_id");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchase_time").toLocalDateTime();
                Ticket temp = new Ticket(username, flightId, purchaseTime);
                temp.setId(id);
                userTickets.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userTickets;
    }

    public List<Ticket> findDateTickets(Timestamp purchaseTimeInput) {
        List<Ticket> dateTickets = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tickets WHERE DATE(purchase_time) = '2024-01-24'");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Long flightId = resultSet.getLong("flight_id");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchase_time").toLocalDateTime();
                Ticket temp = new Ticket(username, flightId, purchaseTime);
                temp.setId(id);
                dateTickets.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dateTickets;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        if(super.save(ticket).isEmpty())
            return Optional.empty();

        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tickets (username, flight_id, purchase_time) VALUES (?, ?, ?)");
            statement.setString(1, ticket.getUsername());
            statement.setLong(2, ticket.getFlightId());
            statement.setTimestamp(3, Timestamp.valueOf(ticket.getPurchaseTime()));
            statement.executeUpdate();
            refreshLastId();
            return Optional.of(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
