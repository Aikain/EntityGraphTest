package com.example.entitygraphtest;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = {"dependency", "childDependency"})
    @Override
    List<Parent> findAll();
}
