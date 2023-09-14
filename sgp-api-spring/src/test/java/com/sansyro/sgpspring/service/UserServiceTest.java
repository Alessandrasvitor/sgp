package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.build.UserRequestBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UserRepository;
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
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private Long ID = 1L;

    @Test
    void listTest() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        List users = service.list();
        verify(repository, times(1)).findAll();
        assertNotNull(users);
    }

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
        when(repository.save(any())).thenReturn(user);
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
        assertThrows(ServiceException.class, () -> service.save(user));
    }

    @Test
    void updateTest() {
        UserRequest user = UserRequestBuild.getBuild();
        when(repository.findById(any())).thenReturn(Optional.of(new User()));
        when(repository.save(any())).thenReturn(UserBuild.getBuild());
        User userSafe = service.update(ID, user);
        verify(repository, times(1)).save(any());
        assertNotNull(userSafe.getId());
        assertNotNull(userSafe.getUserHashCode());
    }

    @Test
    void updateWithErrorNameTest() {
        assertThrows(ServiceException.class, () -> service.update(ID, new UserRequest()));
    }

    @Test
    void updateWithErrorEmailTest() {
        UserRequest user = UserRequestBuild.getBuild();
        user.setEmail(null);
        assertThrows(ServiceException.class, () -> service.update(ID, user));
    }

    @Test
    void updateWithErrorStartViewTest() {
        UserRequest user = UserRequestBuild.getBuild();
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
        assertEquals(user.getPassword(), "123456");
    }

    @Test
    void updateFunctionalitiesTest() {
        User user = UserBuild.getBuild();
        when(repository.findById(any())).thenReturn(Optional.of(user));
        when(repository.save(any())).thenReturn(user);
        assertNotNull(service.updateFunctionalities(ID, new UserRequest()));
        verify(repository, times(1)).findById(any());
    }

}
