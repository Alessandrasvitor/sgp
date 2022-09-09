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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void saveTest() {
        User user = UserBuild.getUser();
        when(repository.save(any())).thenReturn(user);
        service.save(user);
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveWithErrorTest() {
        User user = UserBuild.getUser();
        when(repository.findByEmail(any())).thenReturn(new User());
        assertThrows(ServiceException.class, () -> service.save(user));
        verify(repository, times(1)).findByEmail(any());
    }

    @Test
    void updateTest() {
        UserRequest user = UserRequestBuild.getUser();
        when(repository.findById(any())).thenReturn(Optional.of(new User()));
        when(repository.save(any())).thenReturn(UserBuild.getUser());
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
        UserRequest user = UserRequestBuild.getUser();
        user.setEmail(null);
        assertThrows(ServiceException.class, () -> service.update(ID, user));
    }

    @Test
    void updatePasswordTest() {
        when(repository.findById(any())).thenReturn(Optional.of(new User()));
        when(repository.save(any())).thenReturn(new User());
        service.updatePassword(ID, RandomStringUtils.randomAlphabetic(8));
        verify(repository, times(1)).save(any());
    }

    @Test
    void updatePasswordWithErrorTest() {
        when(repository.findById(any())).thenReturn(Optional.of(new User()));
        assertThrows(ServiceException.class, () -> service.updatePassword(ID, null));
    }

}
