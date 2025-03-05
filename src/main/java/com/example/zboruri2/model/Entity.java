package com.example.zboruri2.model;

import java.util.Objects;

public class Entity<ID extends Comparable<ID>> {
    protected ID id;

    public Entity() {}

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Entity<?>)) {
            return false;
        }
        return Objects.equals(id, ((Entity<?>) obj).getId());
    }
}
