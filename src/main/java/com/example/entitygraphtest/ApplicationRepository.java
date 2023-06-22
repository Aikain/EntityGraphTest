package com.example.entitygraphtest;

import com.example.entitygraphtest.application.Application;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = {"course", "event"})
    @Override
    List<Application> findAll();
}
