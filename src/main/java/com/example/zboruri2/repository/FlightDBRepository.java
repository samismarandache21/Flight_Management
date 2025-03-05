package com.example.zboruri2.repository;

import com.example.zboruri2.model.Client;
import com.example.zboruri2.model.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightDBRepository extends com.example.zboruri2.repository.DBRepository<Long, Flight> {
    public FlightDBRepository(String connectionString, String user, String password) {
        super(connectionString, user, password);
    }

    @Override
    protected void refreshLastId() {
        try {
            Connection connection = DriverManager.getConnection(connectionString, user, password);
            String lastID_query = "SELECT setval(pg_get_serial_sequence('flights', 'id'), (SELECT MAX(id) FROM flights))";
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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM flights")) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String from = resultSet.getString("from_location");
                String to = resultSet.getString("to_location");
                LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landing_time").toLocalDateTime();
                int seats = resultSet.getInt("seats");
                Flight temp = new Flight(from, to, departureTime, landingTime, seats);
                temp.setId(id);
                super.save(temp);
            }

            refreshLastId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Flight> getDayFlights(LocalDateTime date) {
        List<Flight> dayFlights = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(connectionString, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM flights WHERE DATE(departure_time) = '" + date + "'")) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String from = resultSet.getString("from_location");
                String to = resultSet.getString("to_location");
                LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landing_time").toLocalDateTime();
                int seats = resultSet.getInt("seats");
                Flight temp = new Flight(from, to, departureTime, landingTime, seats);
                temp.setId(id);
                dayFlights.add(temp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dayFlights;
    }

    public List<String> findLocations() {
        List<String> locations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT from_location FROM flights");
            while (resultSet.next()) {
                locations.add(resultSet.getString("from_location"));
            }

            resultSet = statement.executeQuery("SELECT DISTINCT to_location FROM flights");
            while (resultSet.next()) {
                String location = resultSet.getString("to_location");
                if(!locations.contains(location))
                    locations.add(location);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return locations;
    }

    public List<Flight> findFilteredFlights(String fromInput, String toInput, Timestamp startOfDay, Timestamp endOfDay) {
        List<Flight> filteredFlights = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM flights WHERE from_location = ? AND to_location = ? AND departure_time BETWEEN ? AND ?");
            statement.setString(1, fromInput);
            statement.setString(2, toInput);
            statement.setTimestamp(3, startOfDay);
            statement.setTimestamp(4, endOfDay);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String from = resultSet.getString("from_location");
                String to = resultSet.getString("to_location");
                LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landing_time").toLocalDateTime();
                int seats = resultSet.getInt("seats");
                Flight temp = new Flight(from, to, departureTime, landingTime, seats);
                temp.setId(id);
                filteredFlights.add(temp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return filteredFlights;
    }
}
