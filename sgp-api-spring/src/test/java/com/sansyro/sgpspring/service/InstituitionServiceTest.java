package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.build.InstituitionBuild;
import com.sansyro.sgpspring.entity.Instituition;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.InstituitionRepository;
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
public class InstituitionServiceTest {

    @InjectMocks
    InstituitionService service;

    @Mock
    private InstituitionRepository repository;

    private Long ID = 1L;

//    @Test
//    void listTest() {
//        when(repository.findAll()).thenReturn(new ArrayList<>());
//        List courses = service.list();
//        verify(repository, times(1)).findAll();
//        assertNotNull(courses);
//    }

    @Test
    void getByIdTest() {
        when(repository.findById(any())).thenReturn(Optional.of(new Instituition()));
        Instituition instituition = service.getById(ID);
        verify(repository, times(1)).findById(any());
        assertNotNull(instituition);
    }

    @Test
    void getByIdWithErrorTest() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> service.getById(ID));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void saveTest() {
        Instituition instituition = InstituitionBuild.getBuild();
        when(repository.save(any())).thenReturn(instituition);
        service.save(instituition);
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveWithErrorTest() {
        assertThrows(ServiceException.class, () -> service.save(new Instituition()));
    }

    @Test
    void updateTest() {
        Instituition instituition = InstituitionBuild.getBuild();
        when(repository.findById(any())).thenReturn(Optional.of(instituition));
        when(repository.save(any())).thenReturn(instituition);
        Instituition inst = service.update(ID, instituition);
        verify(repository, times(1)).save(any());
        assertNotNull(inst);
    }

    @Test
    void updateWithErrorTest() {
        Instituition instituition = InstituitionBuild.getBuild();
        instituition.setAddress("");
        assertThrows(ServiceException.class, () -> service.update(ID, instituition));
    }

    @Test
    void deleteTest() {
        service.delete(ID);
        verify(repository, times(1)).deleteById(any());
    }

}
