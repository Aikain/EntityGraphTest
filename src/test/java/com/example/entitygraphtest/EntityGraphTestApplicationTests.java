package com.example.entitygraphtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntityGraphTestApplicationTests {

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
}
