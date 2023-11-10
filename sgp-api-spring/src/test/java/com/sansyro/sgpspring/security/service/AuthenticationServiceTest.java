package com.sansyro.sgpspring.security.service;

import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.exception.ForbiddenException;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.service.UserService;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService service;
    @Mock
    private UserRepository repository;
    @Mock
    private UserService Userservice;
    @Mock
    private TokenService tokenService;

    User user;

    @BeforeEach
    void setUp() {
        user = UserBuild.getBuild();
        user.setPassword(PASSWORD_DEFAULT);
    }

    @Test
    void loginDefaultTest() throws Exception {
        when(repository.findByEmail(anyString())).thenReturn(user);
        when(tokenService.generateToken(any()))
            .thenReturn(RandomStringUtils.randomAlphanumeric(50));
        when(repository.save(any())).thenReturn(user);
        assertNotNull(service.login(user));
        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any());
    }

    @Test
    void loginDefaultErrorTest() {
        when(repository.findByEmail(anyString())).thenReturn(user);
        assertThrows(ForbiddenException.class, () -> service.login(User.builder()
            .email(RandomStringUtils.randomAlphanumeric(10))
            .userHashCode(RandomStringUtils.randomAlphanumeric(10))
            .password(RandomStringUtils.randomAlphanumeric(10)).build()));
        verify(repository, times(1)).findByEmail(anyString());
    }

    @Test
    void updatePasswordDefaultTest() throws Exception {
        when(repository.findByEmail(anyString())).thenReturn(user);
        when(Userservice.validatePassword(anyString(), any())).thenCallRealMethod();
        when(repository.save(any())).thenReturn(user);
        assertNotNull(service.updatePassword(PASSWORD_DEFAULT, user));
        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any());
    }

    @Test
    void updatePasswordDefaultErrorTest() {
        when(repository.findByEmail(anyString()))
            .thenReturn(User.builder().password(RandomStringUtils.randomAlphanumeric(10)).build());
        assertThrows(ForbiddenException.class, () ->
            service.updatePassword(PASSWORD_DEFAULT, user));
        verify(repository, times(1)).findByEmail(anyString());
    }

    @Test
    void activateUserTest() throws Exception {
        User builder = user.clone();
        user.setPassword(SecurityUtil.cryptPassword(user.getPassword()+user.getUserHashCode()));
        when(repository.findByEmail(anyString())).thenReturn(user);
        when(repository.save(any())).thenReturn(user);
        assertNotNull(service.activateUser(builder));
        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any());
    }

}
