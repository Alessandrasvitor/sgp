package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.build.CourseDTOBuild;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.CourseRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseService service;

    @Mock
    private UserService userservice;

    @Mock
    InstituitionService instituitionService;

    @Mock
    private CourseRepository repository;

    private final Long ID = 1L;

    private Course courseBuild;

    @BeforeEach
    void setUp() {
        courseBuild = CourseBuild.getBuild();
    }

//    @Test
//    void listTest() {
//        when(repository.findAll()).thenReturn(new PageImpl<>(Collections.emptyList()));
//        List courses = service.list();
//        verify(repository, times(1)).findAll();
//        assertNotNull(courses);
//    }

    @Test
    void getByIdTest() {
        when(repository.findById(any())).thenReturn(Optional.of(courseBuild));
        Course course = service.getById(ID);
        verify(repository, times(1)).findById(any());
        assertNotNull(course);
    }

    @Test
    void getByIdWithErrorTest() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> service.getById(ID));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void saveTest() {
        CourseDTO dto = CourseDTOBuild.getBuild();
        Course course = CourseBuild.getBuild();
        when(repository.save(any())).thenReturn(course);
        when(userservice.getById(any())).thenReturn(new User());
        service.save(dto);
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveWithErrorNotNameTest() {
        CourseDTO dto = new CourseDTO();
        assertThrows(ServiceException.class, () -> service.save(dto));
    }

    @Test
    void saveWithErrorNotUserTest() {
        CourseDTO dto = new CourseDTO();
        dto.setName(RandomStringUtils.randomAlphabetic(8));
        assertThrows(ServiceException.class, () -> service.save(dto));
    }

    @Test
    void saveWithErrorNotDescriptionTest() {
        CourseDTO dto = CourseDTOBuild.getBuild();
        dto.setDescription("");
        assertThrows(ServiceException.class, () -> service.save(dto));
    }

//    @Test
//    void updateTest() {
//        CourseDTO dto = CourseDTOBuild.getBuild();
//        when(repository.findById(any())).thenReturn(Optional.of(courseBuild));
//        when(repository.save(any())).thenReturn(courseBuild);
//        when(instituitionService.getById(any())).thenReturn(new Instituition());
//        when(userservice.getById(any())).thenReturn(new User());
//        Course course = service.update(ID, dto);
//        verify(repository, times(1)).save(any());
//        assertNotNull(course);
//    }

    @Test
    void updateWithErrorNotCategoryTest() {
        CourseDTO dto = CourseDTOBuild.getBuild();
        dto.setCategory(null);
        assertThrows(ServiceException.class, () -> service.update(ID, dto));
    }

    @Test
    void updateWithErrorNotInstituitionTest() {
        CourseDTO dto = CourseDTOBuild.getBuild();
        dto.setIdInstituition(null);
        assertThrows(ServiceException.class, () -> service.update(ID, dto));
    }

    @Test
    void startTest() {
        when(repository.findById(any())).thenReturn(Optional.of(courseBuild));
        when(repository.save(any())).thenReturn(courseBuild);
        Course course = service.start(ID);
        assertNotNull(course);
    }

    @Test
    void finishAprovedTest() {
        when(repository.save(any())).thenReturn(courseBuild);
        when(repository.findById(any())).thenReturn(Optional.of(courseBuild));
        Course course = service.finish(ID, Float.parseFloat("8.5"));
        assertNotNull(course);
    }

    @Test
    void finishReprovedTest() {
        when(repository.findById(any())).thenReturn(Optional.of(courseBuild));
        when(repository.save(any())).thenReturn(courseBuild);
        Course course = service.finish(ID, Float.parseFloat("5.5"));
        assertNotNull(course);
    }

    @Test
    void deleteTest() {
        service.delete(ID);
        verify(repository, times(1)).deleteById(any());
    }

}
