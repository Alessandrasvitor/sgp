package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.CourseRepository;
import com.sansyro.sgpspring.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstituitionService instituitionService ;

    @Autowired
    private UserService userService;

    public List<Course> list() {
        User user = userService.getUserLogger();
        if(nonNull(user) && nonNull(user.getId())) {
            return courseRepository.list(user.getId());
        }
        return Collections.emptyList();
    }

    public Page<Course> list(Pageable pageable) {
        User user = userService.getUserLogger();
        if(nonNull(user) && nonNull(user.getId())) {
            return courseRepository.list(user.getId(), pageable);
        }
        return new PageImpl<>(Collections.emptyList());
    }

    public Course getById(Long id) {
        Optional<Course> courseOp = courseRepository.findById(id);
        if(courseOp.isPresent()) {
            return courseOp.get();
        }
        throw new ServiceException("Curso não encontrado");
    }

    public Course save(CourseDTO dto) {
        validateNotNull(dto);
        dto.setStatus(StatusEnum.PENDING.name());
        Course course = Course.mapper(dto);
        validateDuplicate(course);
        course.setInstituition(instituitionService.getById(dto.getIdInstituition()));
        course.setUser(userService.getUserLogger());
        course.setPriority(5);
        return courseRepository.save(course);
    }

    private void validateDuplicate(Course course) {
        Course courseDuplicated = courseRepository.findByName(course.getName());
        if(nonNull(courseDuplicated) && !courseDuplicated.equals(course)) {
            throw new ServiceException("Curso já existe na base de dados.");
        }
    }

    public Course update(Long id, CourseDTO dto) {
        validateNotNull(dto);
        Course course = getById(id);
        validateDuplicate(course);
        course.setInstituition(instituitionService.getById(dto.getIdInstituition()));
        course.setName(dto.getName());
        course.setCategory(CategoryEnum.valueOf(dto.getCategory()));
        course.setDescription(dto.getDescription());
        course.setPriority(dto.getPriority());
        return courseRepository.save(course);
    }

    public Course start(Long id) {
        Course course = getById(id);
        course.setStartDate(new Date());
        course.setStatus(StatusEnum.PROGRESS);
        return courseRepository.save(course);
    }

    public Course finish(Long id, float notation) {
        Course course = getById(id);
        course.setNotation(notation);
        if(notation < 7) {
            course.setStatus(StatusEnum.FAIL);
        } else {
            course.setStatus(StatusEnum.FINISH);
            course.setStartDate(new Date());
        }
        return courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    private void validateNotNull(CourseDTO course) {
        if(GeralUtil.stringNullOrEmpty(course.getName())){
            throw new ServiceException("Nome do curso é obrigatório");
        }
        if(GeralUtil.stringNullOrEmpty(course.getDescription())){
            throw new ServiceException("Descrição do course é obrigatória");
        }
        if(isNull(course.getCategory())){
            throw new ServiceException("Categoria do course é obrigatória");
        }
        if(isNull(course.getIdInstituition())){
            throw new ServiceException("Instituição do course é obrigatória");
        }
    }

}
