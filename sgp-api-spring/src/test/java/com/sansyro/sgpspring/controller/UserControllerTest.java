package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
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
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private Long ID = 1L;

    @Test
    void listTest() {
        when(service.list()).thenReturn(new ArrayList<>());
        ResponseEntity response = controller.list();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getByIdTest() {
        User userBuild = UserBuild.getBuild();
        when(service.getById(any())).thenReturn(userBuild);
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
        ResponseEntity response = controller.save(new User());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void saveWithBadRequestTest() {
        doThrow(new ServiceException()).when(service).save(any());
        ResponseEntity response = controller.save(new User());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void saveWithErrorTest() {
        doThrow(new RuntimeException()).when(service).save(any());
        ResponseEntity response = controller.save(new User());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateTest() {
        User userBuild = UserBuild.getBuild();
        when(service.update(any(), any())).thenReturn(userBuild);
        ResponseEntity response = controller.update(ID, new UserRequest());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateWithBadRequestTest() {
        when(service.update(any(), any())).thenThrow(new ServiceException());
        ResponseEntity response = controller.update(ID, new UserRequest());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateWithErrorTest() {
        when(service.update(any(), any())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.update(ID, new UserRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    /*

    @Test
    void updatePasswordTest() {
        User userBuild = UserBuild.getUser();
        when(service.updatePassword(any(), any())).thenReturn(userBuild);
        ResponseEntity response = controller.updatePassword(ID, RandomStringUtils.randomAlphabetic(8));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updatePasswordWithBadRequestTest() {
        when(service.updatePassword(any(), any())).thenThrow(new ServiceException());
        ResponseEntity response = controller.updatePassword(ID, RandomStringUtils.randomAlphabetic(8));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updatePasswordWithErrorTest() {
        when(service.updatePassword(any(), any())).thenThrow(new RuntimeException());
        ResponseEntity response = controller.updatePassword(ID, RandomStringUtils.randomAlphabetic(8));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
*/
    @Test
    void getWithPasswordTest() {
        User userBuild = UserBuild.getBuild();
        when(service.getById(any())).thenReturn(userBuild);
        ResponseEntity response = controller.getWithPassword(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getWithPasswordWithBadRequestTest() {
        when(service.getById(any())).thenThrow(new ServiceException());
        ResponseEntity response = controller.getWithPassword(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getWithPasswordWithErrorTest() {
        when(service.getById(any())).thenThrow(new NullPointerException());
        ResponseEntity response = controller.getWithPassword(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
