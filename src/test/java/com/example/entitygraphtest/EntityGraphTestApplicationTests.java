package com.example.entitygraphtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntityGraphTestApplicationTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ParentRepository parentRepository;

    @Test
    void test() {
        Child child = new Child();
        child.setDependency(new Dependency("test"));
        child.setChildDependency(new Dependency("test2"));
        parentRepository.save(child);

        List<Parent> parentList = parentRepository.findAll();

        assertThat(parentList).hasSize(1);
        assertEquals("test", parentList.get(0).getDependency().getTest());
        assertEquals("test2", ((Child)parentList.get(0)).getChildDependency().getTest());
    }

    @Transactional
    @Test
    void test2() {
        Child child = new Child();
        child.setDependency(new Dependency("test"));
        child.setChildDependency(new Dependency("test2"));
        entityManager.persist(child);
        entityManager.flush();
        entityManager.clear();

        EntityGraph<Parent> entityGraph = entityManager.createEntityGraph(Parent.class);
        entityGraph.addAttributeNodes("dependency");
        entityGraph.addAttributeNodes("childDependency");

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);

        Parent obj = entityManager.find(Parent.class, child.getId(), properties);
        assertTrue(Hibernate.isInitialized(obj.getDependency()));                   // OK
        assertTrue(Hibernate.isInitialized(((Child)obj).getChildDependency()));     // Fail
    }

    @Transactional
    @Test
    void test3() {
        Child child = new Child();
        child.setDependency(new Dependency("test"));
        child.setChildDependency(new Dependency("test2"));
        entityManager.persist(child);
        entityManager.flush();
        entityManager.clear();

        EntityGraph<Child> entityGraph = entityManager.createEntityGraph(Child.class);
        entityGraph.addAttributeNodes("dependency");
        entityGraph.addAttributeNodes("childDependency");

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);

        Parent obj = entityManager.find(Parent.class, child.getId(), properties);
        assertTrue(Hibernate.isInitialized(obj.getDependency()));                   // OK
        assertTrue(Hibernate.isInitialized(((Child)obj).getChildDependency()));     // OK
    }
}
