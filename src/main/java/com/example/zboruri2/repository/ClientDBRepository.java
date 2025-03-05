package com.example.zboruri2.repository;

import com.example.zboruri2.model.Client;

import java.sql.*;
import java.util.Optional;

public class ClientDBRepository extends DBRepository<Long, Client> {
    public ClientDBRepository(String connectionString, String user, String password) {
        super(connectionString, user, password);
    }

    @Override
    protected void refreshLastId() {
        try {
            Connection connection = DriverManager.getConnection(connectionString, user, password);
            String lastID_query = "SELECT setval(pg_get_serial_sequence('clients', 'id'), (SELECT MAX(id) FROM clients))";
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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM clients")) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                Client temp = new Client(username, name);
                temp.setId(id);
                super.save(temp);
            }

            refreshLastId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Client> findOneByUsername(String usernameInput) {
        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients WHERE username = ?");
            statement.setString(1, usernameInput);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                Client temp = new Client(username, name);
                temp.setId(id);
                return Optional.of(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
