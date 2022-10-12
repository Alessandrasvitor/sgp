package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.build.CourseRequestBuild;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.dto.CourseRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CourseControllerTest {

    @InjectMocks
    private CourseController controller;

    @Mock
    private CourseService service;

    private Long ID = 1L;

    private Course courseBuild;

    private CourseRequest courseRequestBuild;

    @BeforeEach
    void setUp() {
        courseBuild = CourseBuild.getBuild();
        courseRequestBuild = CourseRequestBuild.getBuild();
    }

    @Test
    void listTest() {
        when(service.list()).thenReturn(new ArrayList<>());
        ResponseEntity response = controller.list();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getByIdTest() {
        Course courseBuild = CourseBuild.getBuild();
        when(service.getById(anyLong())).thenReturn(courseBuild);
        ResponseEntity response = controller.getById(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getByIdWithBadRequestTest() {
        when(service.getById(anyLong())).thenThrow(new ServiceException());
        ResponseEntity response = controller.getById(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getByIdWithErrorTest() {
        when(service.getById(anyLong())).thenThrow(new NullPointerException());
        ResponseEntity response = controller.getById(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void saveTest() {
        ResponseEntity response = controller.save(courseRequestBuild);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void saveWithBadRequestTest() {
        doThrow(new ServiceException()).when(service).save(any());
        ResponseEntity response = controller.save(courseRequestBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void saveWithErrorTest() {
        doThrow(new RuntimeException()).when(service).save(any());
        ResponseEntity response = controller.save(courseRequestBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateTest() {
        Course courseBuild = CourseBuild.getBuild();
        when(service.update(anyLong(), any())).thenReturn(courseBuild);
        ResponseEntity response = controller.update(ID, new CourseRequest());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateWithBadRequestTest() {
        when(service.update(anyLong(), any())).thenThrow(new ServiceException());
        ResponseEntity response = controller.update(ID, new CourseRequest());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateWithErrorTest() {
        when(service.update(anyLong(), any())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.update(ID, new CourseRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void startWithBadRequestTest() {
        when(service.start(anyLong())).thenThrow(new ServiceException());
        ResponseEntity response = controller.start(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void startTest() {
        when(service.start(anyLong())).thenReturn(courseBuild);
        ResponseEntity response = controller.start(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void startWithExceptionTest() {
        when(service.start(anyLong())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.start(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteTest() {
        ResponseEntity response = controller.delete(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteWithErrorTest() {
        doThrow(new RuntimeException()).when(service).delete(anyLong());
        ResponseEntity response = controller.delete(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteWithBadRequestTest() {
        doThrow(new ServiceException()).when(service).delete(anyLong());
        ResponseEntity response = controller.delete(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void finishTest() {
        when(service.finish(anyLong(), anyFloat())).thenReturn(courseBuild);
        ResponseEntity response = controller.finish(ID, courseRequestBuild);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void finishWithErrorTest() {
        when(service.finish(anyLong(), anyFloat())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.finish(ID, courseRequestBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void finishWithBadRequestTest() {
        when(service.finish(anyLong(), anyFloat())).thenThrow(new ServiceException());
        ResponseEntity response = controller.finish(ID, courseRequestBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
