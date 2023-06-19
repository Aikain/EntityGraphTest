package com.example.entitygraphtest;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Dependency extends AbstractPersistable<Long> {

    private String test;

    public Dependency() {
    }

    public Dependency(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String value) {
        this.test = value;
    }
}
