package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.build.CourseDTOBuild;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CourseControllerTest {

    @InjectMocks
    private CourseController controller;

    @Mock
    private CourseService service;

    private final Long ID = 1L;

    private CourseDTO courseDTOBuild;

    private Course courseBuild;

    @BeforeEach
    void setUp() {
        courseDTOBuild = CourseDTOBuild.getBuild();
        courseBuild = CourseBuild.getBuild();
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
        when(service.getById(anyLong())).thenThrow(ServiceException.class);
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
        ResponseEntity response = controller.save(courseDTOBuild);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void saveWithBadRequestTest() {
        doThrow(ServiceException.class).when(service).save(any());
        ResponseEntity response = controller.save(courseDTOBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void saveWithErrorTest() {
        doThrow(new RuntimeException()).when(service).save(any());
        ResponseEntity response = controller.save(courseDTOBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateTest() {
        when(service.update(anyLong(), any())).thenReturn(courseBuild);
        ResponseEntity response = controller.update(ID, new CourseDTO());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateWithBadRequestTest() {
        when(service.update(anyLong(), any())).thenThrow(ServiceException.class);
        ResponseEntity response = controller.update(ID, new CourseDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateWithErrorTest() {
        when(service.update(anyLong(), any())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.update(ID, new CourseDTO());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void startWithBadRequestTest() {
        when(service.start(anyLong())).thenThrow(ServiceException.class);
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
        doThrow(ServiceException.class).when(service).delete(anyLong());
        ResponseEntity response = controller.delete(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void finishTest() {
        when(service.finish(anyLong(), anyFloat())).thenReturn(courseBuild);
        ResponseEntity response = controller.finish(ID, courseDTOBuild);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void finishWithErrorTest() {
        when(service.finish(anyLong(), anyFloat())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.finish(ID, courseDTOBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void finishWithBadRequestTest() {
        when(service.finish(anyLong(), anyFloat())).thenThrow(ServiceException.class);
        ResponseEntity response = controller.finish(ID, courseDTOBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
