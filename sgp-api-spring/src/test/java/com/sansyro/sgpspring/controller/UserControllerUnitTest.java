package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.sansyro.sgpspring.exception.MessageError;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private final Long ID = 1L;
    private ServiceException serviceException;

    @BeforeEach
    void setUp() {
        serviceException = new ServiceException(MSG_FIELDS_NOT_FILLED);
    }

    @Test
    void getByIdServiceExceptionTest() {
        when(service.getById(anyLong())).thenThrow(serviceException);
        var response = controller.getById(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MSG_FIELDS_NOT_FILLED.getMessage(), ((MessageError)response.getBody()).getUserMessage());
        assertEquals(MSG_FIELDS_NOT_FILLED.getCode(), ((MessageError)response.getBody()).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void getByIdExceptionTest() {
        when(service.getById(anyLong())).thenThrow(NullPointerException.class);
        var response = controller.getById(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void resetPasswordServiceExceptionTest() {
        doThrow(serviceException).
            when(service).resetPassword(anyLong());
        var response = controller.resetPassword(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(MSG_FIELDS_NOT_FILLED.getCode(), ((MessageError)response.getBody()).getCode());
        assertEquals(MSG_FIELDS_NOT_FILLED.getMessage(), ((MessageError)response.getBody()).getUserMessage());
    }

    @Test
    void resetPasswordExceptionTest() {
        doThrow(NullPointerException.class).
            when(service).resetPassword(anyLong());
        var response = controller.resetPassword(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getWithPasswordServiceExceptionTest() {
        doThrow(serviceException).
            when(service).getById(anyLong());
        var response = controller.getWithPassword(ID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(MSG_FIELDS_NOT_FILLED.getCode(), ((MessageError)response.getBody()).getCode());
        assertEquals(MSG_FIELDS_NOT_FILLED.getMessage(), ((MessageError)response.getBody()).getUserMessage());
    }

    @Test
    void getWithPasswordExceptionTest() {
        doThrow(NullPointerException.class).
            when(service).getById(anyLong());
        var response = controller.getWithPassword(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
