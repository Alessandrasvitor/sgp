package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.build.InstituitionBuild;
import com.sansyro.sgpspring.entity.Instituition;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.InstituitionService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class InstituitionControllerTest {

    @InjectMocks
    private InstituitionController controller;

    @Mock
    private InstituitionService service;

    private Long ID = 1L;

    private Instituition instituitionBuild;

    @BeforeEach
    void setUp() {
        instituitionBuild = InstituitionBuild.getBuild();
    }

    @Test
    void listTest() {
        when(service.list()).thenReturn(new ArrayList<>());
        ResponseEntity response = controller.list();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getByIdTest() {
        Instituition instituitionBuild = InstituitionBuild.getBuild();
        when(service.getById(any())).thenReturn(instituitionBuild);
        ResponseEntity response = controller.getById(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getByIdWithBadRequestTest() {
        when(service.getById(any())).thenThrow(new ServiceException());
        ResponseEntity response = controller.getById(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getByIdWithErrorTest() {
        when(service.getById(any())).thenThrow(new NullPointerException());
        ResponseEntity response = controller.getById(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void saveTest() {
        ResponseEntity response = controller.save(new Instituition());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void saveWithBadRequestTest() {
        doThrow(new ServiceException()).when(service).save(any());
        ResponseEntity response = controller.save(new Instituition());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void saveWithErrorTest() {
        doThrow(new RuntimeException()).when(service).save(any());
        ResponseEntity response = controller.save(new Instituition());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateTest() {
        Instituition instituitionBuild = InstituitionBuild.getBuild();
        when(service.update(any(), any())).thenReturn(instituitionBuild);
        ResponseEntity response = controller.update(ID, new Instituition());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateWithBadRequestTest() {
        when(service.update(any(), any())).thenThrow(new ServiceException());
        ResponseEntity response = controller.update(ID, new Instituition());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateWithErrorTest() {
        when(service.update(any(), any())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.update(ID, new Instituition());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updatePasswordTest() {
        when(service.update(any(), any())).thenReturn(instituitionBuild);
        ResponseEntity response = controller.update(ID, instituitionBuild);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteWithBadRequestTest() {
        doThrow(new ServiceException()).when(service).delete(any());
        ResponseEntity response = controller.delete(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteWithErrorTest() {
        doThrow(new RuntimeException()).when(service).delete(any());
        ResponseEntity response = controller.delete(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteTest() {
        ResponseEntity response = controller.delete(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
