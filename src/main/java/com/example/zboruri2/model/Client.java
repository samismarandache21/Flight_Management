package com.example.zboruri2.model;

public class Client extends Entity<Long> {
    private String username;
    private String name;

    public Client() {}

    public Client(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Client[id=%d, username='%s', name='%s']", id, username, name);
    }
}

