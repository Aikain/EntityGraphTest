package com.example.entitygraphtest.application;

import com.example.entitygraphtest.action.Action;
import com.example.entitygraphtest.action.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseApplication extends Application {

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    @Override
    public Action getAction() {
        return null;
    }
}
