package com.example.entitygraphtest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public abstract class Parent extends AbstractPersistable<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    private Dependency dependency;

    public Dependency getDependency() {
        return dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }
}
