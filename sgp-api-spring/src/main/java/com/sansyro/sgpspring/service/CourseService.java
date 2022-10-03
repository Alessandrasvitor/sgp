package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.dto.CourseRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.CourseRepository;
import com.sansyro.sgpspring.repository.InstituitionRepository;
import com.sansyro.sgpspring.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstituitionService instituitionService ;

    @Autowired
    private UserService userService;

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Course getById(Long id) {
        Optional<Course> courseOp = courseRepository.findById(id);
        if(courseOp.isPresent()) {
            return courseOp.get();
        }
        throw new ServiceException("Curso não encontrado");
    }

    public Course save(CourseRequest request) {
        validateNotNull(request);
        //validateDuplicate(course);
        Course course = request.mapperEntity();
        course.setStatus(StatusEnum.PENDING);
        course.setInstituition(instituitionService.getById(request.getIdInstituition()));
        course.setUser(userService.getById(request.getIdUser()));
        course.setPriority(5);
        return courseRepository.save(course);
    }

    public Course update(Long id, CourseRequest request) {
        validateNotNull(request);
        //validateDuplicate(course.getAddress());
        Course course = getById(id);
        course.mapperRequest(request);
        course.setInstituition(instituitionService.getById(request.getIdInstituition()));
        course.setUser(userService.getById(request.getIdUser()));
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

    private void validateNotNull(CourseRequest course) {
        if(GeralUtil.stringNullOrEmpty(course.getName())){
            throw new ServiceException("Nome do curso é obrigatório");
        }
        if(course.getIdUser() == null){
            throw new ServiceException("Usuário do curso é obrigatório");
        }
        if(GeralUtil.stringNullOrEmpty(course.getDescription())){
            throw new ServiceException("Descrição do course é obrigatória");
        }
        if(course.getCategory() == null){
            throw new ServiceException("Categoria do course é obrigatória");
        }
        if(course.getIdInstituition() == null){
            throw new ServiceException("Instituição do course é obrigatória");
        }
    }

}
