package com.example.entitygraphtest.application;

import com.example.entitygraphtest.action.Action;
import com.example.entitygraphtest.action.Event;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EventApplication extends Application {

    @ManyToOne(cascade = CascadeType.ALL)
    private Event event;

    @Override
    public Action getAction() {
        return event;
    }
}
