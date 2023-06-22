package com.example.entitygraphtest.application;

import com.example.entitygraphtest.action.Action;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Transient;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Application extends AbstractPersistable<Long> {

    @Transient
    public abstract Action getAction();
}
