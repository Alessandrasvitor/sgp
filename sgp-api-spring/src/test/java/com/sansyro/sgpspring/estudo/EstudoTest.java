package com.sansyro.sgpspring.estudo;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EstudoTest {

    @Test
    void cloneTest() {
        Course course = CourseBuild.getBuild();
        Course courseClone = new Course();
        try {
            courseClone = (Course) course.clone();
        } catch (Exception e) {

        }
        System.out.println(course.getDescription());
        System.out.println(courseClone.getDescription());
        assertEquals(course.getDescription(), courseClone.getDescription());
    }

}
