package com.example.zboruri2.repository;

import com.example.zboruri2.model.Entity;

public abstract class DBRepository<ID extends Comparable<ID>, E extends Entity<ID>> extends com.example.zboruri2.repository.InMemoryRepository<ID, E> {
    protected String connectionString;
    protected String user;
    protected String password;

    public DBRepository(String connectionString, String user, String password) {
        this.connectionString = connectionString;
        this.user = user;
        this.password = password;

        read();
    }

    protected abstract void read();
    protected abstract void refreshLastId();
}
