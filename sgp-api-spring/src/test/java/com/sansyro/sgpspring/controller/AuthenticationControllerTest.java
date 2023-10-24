package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.security.service.AuthenticationService;
import com.sansyro.sgpspring.service.UserService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController controller;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    private final Long ID = 1L;

    private User userBuild;

    @BeforeEach
    void setUp() {
        userBuild = UserBuild.getBuild();
    }

    @Test
    void loginTest() {
        when(authenticationService.login(any())).thenReturn(userBuild);
        ResponseEntity response = controller.login(userBuild);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void loginWithErrorTest() {
        when(authenticationService.login(any())).thenThrow(RuntimeException.class);
        ResponseEntity response = controller.login(userBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void loginWithBadRequestTest() {
        when(authenticationService.login(any())).thenThrow(ServiceException.class);
        ResponseEntity response = controller.login(userBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void logoutTest() {
        ResponseEntity response = controller.logout(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void logoutWithErrorTest() {
        doThrow(RuntimeException.class).when(authenticationService).logout(any());
        ResponseEntity response = controller.logout(ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void registerTest() {
        when(authenticationService.register(any())).thenReturn(userBuild);
        ResponseEntity response = controller.register(userBuild);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void registerWithErrorTest() {
        when(authenticationService.register(any())).thenThrow(RuntimeException.class);
        ResponseEntity response = controller.register(userBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void registerTokenWithBadRequestTest() {
        when(authenticationService.register(any())).thenThrow(ServiceException.class);
        ResponseEntity response = controller.register(userBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void changePasswordTest() {
        when(authenticationService.updatePassword(any())).thenReturn(userBuild);
        ResponseEntity response = controller.changePassword(userBuild);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void changePasswordWithErrorTest() {
        when(authenticationService.updatePassword(any())).thenThrow(RuntimeException.class);
        ResponseEntity response = controller.changePassword(userBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void changePasswordWithBadRequestTest() {
        when(authenticationService.updatePassword(any())).thenThrow(UsernameNotFoundException.class);
        ResponseEntity response = controller.changePassword(userBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateTokenTest() {
        when(authenticationService.updateToken(any())).thenReturn(userBuild);
        ResponseEntity response = controller.updateToken(userBuild);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateTokenWithErrorTest() {
        when(authenticationService.updateToken(any())).thenThrow(RuntimeException.class);
        ResponseEntity response = controller.updateToken(userBuild);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateTokenWithBadRequestTest() {
        when(authenticationService.updateToken(any())).thenThrow(ServiceException.class);
        ResponseEntity response = controller.updateToken(userBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateTokenWithBadRequestErrorTest() {
        when(authenticationService.updateToken(any())).thenThrow(UsernameNotFoundException.class);
        ResponseEntity response = controller.updateToken(userBuild);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
