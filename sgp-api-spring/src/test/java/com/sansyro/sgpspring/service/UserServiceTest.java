package com.sansyro.sgpspring.service;

import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UserRepository;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private final Long ID = 1L;

    @Test
    void getByIdTest() {
        when(repository.findById(any())).thenReturn(Optional.of(new User()));
        User user = service.getById(ID);
        verify(repository, times(1)).findById(any());
        assertNotNull(user);
    }

    @Test
    void getByIdWithErrorTest() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> service.getById(ID));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void getByEmailTest() {
        when(repository.findByEmail(any())).thenReturn(new User());
        User user = service.getByEmail(RandomStringUtils.randomAlphabetic(8));
        verify(repository, times(1)).findByEmail(any());
        assertNotNull(user);
    }

    @Test
    void getByEmailWithErrorTest() {
        when(repository.findByEmail(any())).thenReturn(null);
        assertThrows(ServiceException.class, () -> service.getByEmail(RandomStringUtils.randomAlphabetic(8)));
        verify(repository, times(1)).findByEmail(any());
    }

    @Test
    void saveTest() {
        User user = UserBuild.getBuild();
        service.save(user);
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveWithErrorTest() {
        User user = UserBuild.getBuild();
        when(repository.findByEmail(any())).thenReturn(new User());
        assertThrows(ServiceException.class, () -> service.save(user));
        verify(repository, times(1)).findByEmail(any());
    }

    @Test
    void saveWithErrorNotPasswordTest() {
        User user = UserBuild.getBuild();
        user.setPassword(null);
        assertDoesNotThrow(() -> service.save(user));
    }

    @Test
    void updateTest() {
        UserDTO user = UserDTO.mapper(UserBuild.getBuild());
        when(repository.findById(any())).thenReturn(Optional.of(new User()));
        when(repository.save(any())).thenReturn(UserBuild.getBuild());
        User userSafe = service.update(ID, user);
        verify(repository, times(1)).save(any());
        assertNotNull(userSafe.getId());
        assertNotNull(userSafe.getUserHashCode());
    }

    @Test
    void updateWithErrorNameTest() {
        assertThrows(ServiceException.class, () -> service.update(ID, new UserDTO()));
    }

    @Test
    void updateWithErrorEmailTest() {
        UserDTO user = UserDTO.mapper(UserBuild.getBuild());
        user.setEmail(null);
        assertThrows(ServiceException.class, () -> service.update(ID, user));
    }

    @Test
    void updateWithErrorStartViewTest() {
        UserDTO user = UserDTO.mapper(UserBuild.getBuild());
        user.setStartView(null);
        assertThrows(ServiceException.class, () -> service.update(ID, user));
    }

    @Test
    void resetPasswordTest() {
        User user = UserBuild.getBuild();
        when(repository.findById(any())).thenReturn(Optional.of(user));
        when(repository.save(any())).thenReturn(user);
        service.resetPassword(ID);
        verify(repository, times(1)).save(any());
        assertEquals(user.getPassword(), PASSWORD_DEFAULT);
    }

    @Test
    void updateFunctionalitiesTest() {
        User user = UserBuild.getBuild();
        when(repository.findById(any())).thenReturn(Optional.of(user));
        when(repository.save(any())).thenReturn(user);
        assertNotNull(service.updateFunctionalities(ID, new UserDTO()));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void getUserLoggerNullTest() {
        assertNull(service.getUserLogger());
    }

}
