package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.build.CourseRequestBuild;
import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.Instituition;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.CourseRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.CourseRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseService service;

    @Mock
    private UserService userservice;

    @Mock
    InstituitionService instituitionService;

    @Mock
    private CourseRepository repository;

    private Long ID = 1L;

    @Test
    void listTest() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        List courses = service.list();
        verify(repository, times(1)).findAll();
        assertNotNull(courses);
    }

    @Test
    void getByIdTest() {
        when(repository.findById(any())).thenReturn(Optional.of(new Course()));
        Course course = service.getById(ID);
        verify(repository, times(1)).findById(any());
        assertNotNull(course);
    }

    @Test
    void getByIdWithErrorTest() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> service.getById(ID));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void saveTest() {
        CourseRequest courseRequest = CourseRequestBuild.getBuild();
        Course course = CourseBuild.getBuild();
        when(repository.save(any())).thenReturn(course);
        when(userservice.getById(any())).thenReturn(new User());
        service.save(courseRequest);
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveWithErrorNotNameTest() {
        CourseRequest courseRequest = new CourseRequest();
        assertThrows(ServiceException.class, () -> service.save(courseRequest));
    }

    @Test
    void saveWithErrorNotUserTest() {
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setName(RandomStringUtils.randomAlphabetic(8));
        assertThrows(ServiceException.class, () -> service.save(courseRequest));
    }

    @Test
    void saveWithErrorNotDescriptionTest() {
        CourseRequest courseRequest = CourseRequestBuild.getBuild();
        courseRequest.setDescription("");
        assertThrows(ServiceException.class, () -> service.save(courseRequest));
    }

    @Test
    void updateTest() {
        CourseRequest courseRequest = CourseRequestBuild.getBuild();
        when(repository.findById(any())).thenReturn(Optional.of(new Course()));
        when(repository.save(any())).thenReturn(new Course());
        when(instituitionService.getById(any())).thenReturn(new Instituition());
        Course course = service.update(ID, courseRequest);
        verify(repository, times(1)).save(any());
        assertNotNull(course);
    }

    @Test
    void updateWithErrorNotCategoryTest() {
        CourseRequest courseRequest = CourseRequestBuild.getBuild();
        courseRequest.setCategory(null);
        assertThrows(ServiceException.class, () -> service.update(ID, courseRequest));
    }

    @Test
    void updateWithErrorNotInstituitionTest() {
        CourseRequest courseRequest = CourseRequestBuild.getBuild();
        courseRequest.setIdInstituition(null);
        assertThrows(ServiceException.class, () -> service.update(ID, courseRequest));
    }

    @Test
    void startTest() {
        when(repository.findById(any())).thenReturn(Optional.of(new Course()));
        when(repository.save(any())).thenReturn(new Course());
        Course course = service.start(ID);
        assertNotNull(course);
    }

    @Test
    void finishAprovedTest() {
        when(repository.save(any())).thenReturn(new Course());
        when(repository.findById(any())).thenReturn(Optional.of(new Course()));
        Course course = service.finish(ID, Float.valueOf("8.5"));
        assertNotNull(course);
    }

    @Test
    void finishReprovedTest() {
        when(repository.findById(any())).thenReturn(Optional.of(new Course()));
        when(repository.save(any())).thenReturn(new Course());
        Course course = service.finish(ID, Float.valueOf("5.5"));
        assertNotNull(course);
    }

    @Test
    void deleteTest() {
        service.delete(ID);
        verify(repository, times(1)).deleteById(any());
    }

}
