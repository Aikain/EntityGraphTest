package com.example.entitygraphtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.entitygraphtest.action.Course;
import com.example.entitygraphtest.action.Event;
import com.example.entitygraphtest.application.Application;
import com.example.entitygraphtest.application.CourseApplication;
import com.example.entitygraphtest.application.EventApplication;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntityGraphTestApplicationTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    void test() {
        CourseApplication courseApplication = new CourseApplication();
        courseApplication.setCourse(new Course());
        EventApplication eventApplication = new EventApplication();
        eventApplication.setEvent(new Event());

        applicationRepository.save(courseApplication);
        applicationRepository.save(eventApplication);

        List<Application> applicationList = applicationRepository.findAll();

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(applicationList).hasSize(2);
        softAssertions.assertThat(Hibernate.isInitialized(applicationList.get(0).getAction())).isTrue();    // OK???
        softAssertions.assertThat(Hibernate.isInitialized(applicationList.get(1).getAction())).isTrue();    // Fail

        softAssertions.assertAll();
    }

    @Transactional
    @Test
    void test2() {
        CourseApplication courseApplication = new CourseApplication();
        courseApplication.setCourse(new Course());
        EventApplication eventApplication = new EventApplication();
        eventApplication.setEvent(new Event());

        entityManager.persist(courseApplication);
        entityManager.persist(eventApplication);
        entityManager.flush();
        entityManager.clear();

        EntityGraph<Application> entityGraph = entityManager.createEntityGraph(Application.class);
        entityGraph.addAttributeNodes("course");
        entityGraph.addAttributeNodes("event");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);

        Application courseApplication1 = entityManager.find(Application.class, courseApplication.getId(), properties);
        Application eventApplication1 = entityManager.find(Application.class, eventApplication.getId(), properties);


        EntityGraph<CourseApplication> courseEntityGraph = entityManager.createEntityGraph(CourseApplication.class);
        courseEntityGraph.addAttributeNodes("course");
        properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", courseEntityGraph);

        CourseApplication courseApplication2 = entityManager.find(CourseApplication.class, courseApplication.getId(), properties);


        EntityGraph<EventApplication> eventEntityGraph = entityManager.createEntityGraph(EventApplication.class);
        eventEntityGraph.addAttributeNodes("event");
        properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", eventEntityGraph);

        EventApplication eventApplication2 = entityManager.find(EventApplication.class, eventApplication.getId(), properties);


        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(Hibernate.isInitialized(courseApplication1.getAction())).isTrue();    // OK???
        softAssertions.assertThat(Hibernate.isInitialized(courseApplication2.getAction())).isTrue();    // OK

        softAssertions.assertThat(Hibernate.isInitialized(eventApplication1.getAction())).isTrue();     // FAIL
        softAssertions.assertThat(Hibernate.isInitialized(eventApplication2.getAction())).isTrue();     // FAIL

        softAssertions.assertAll();
    }
}
