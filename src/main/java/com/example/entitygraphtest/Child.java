package com.example.entitygraphtest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Child extends Parent {

    @ManyToOne(cascade = CascadeType.ALL)
    private Dependency childDependency;

    public Dependency getChildDependency() {
        return childDependency;
    }

    public void setChildDependency(Dependency childDependency) {
        this.childDependency = childDependency;
    }
}
