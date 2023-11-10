package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_CHECKER_CODE_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.exception.ForbiddenException;
import com.sansyro.sgpspring.exception.MessageError;
import com.sansyro.sgpspring.security.service.AuthenticationService;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerUnitTest {

    @InjectMocks
    private AuthenticationController controller;

    @Mock
    private AuthenticationService service;

    private final Long ID = 1L;

    @Test
    void activateUserForbiddenExceptionTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ForbiddenException exception = new ForbiddenException(MSG_CHECKER_CODE_INVALID);
        when(service.activateUser(any())).thenThrow(exception);
        var response = controller.active(UserBuild.getBuild());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(MSG_CHECKER_CODE_INVALID.getMessage(), ((MessageError)response.getBody()).getUserMessage());
        assertEquals(MSG_CHECKER_CODE_INVALID.getCode(), ((MessageError)response.getBody()).getCode());
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
    }

    @Test
    void activeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        when(service.activateUser(any())).thenReturn(UserBuild.getBuild());
        var response = controller.active(UserBuild.getBuild());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
