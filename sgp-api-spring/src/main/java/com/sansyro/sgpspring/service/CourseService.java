package com.sansyro.sgpspring.service;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_COURSE_DOUBLE;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_COURSE_NOT_FOUND;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.CourseRepository;
import com.sansyro.sgpspring.util.GeneralUtil;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstituitionService instituitionService;

    @Autowired
    private UserService userService;

    public List<Course> list() {
        User user = userService.getUserLogger();
        return courseRepository.list(user.getId());
    }

    public Page<Course> list(Pageable pageable) {
        User user = userService.getUserLogger();
        return courseRepository.list(user.getId(), pageable);
    }

    public Course getById(Long id) {
        Optional<Course> courseOp = courseRepository.findById(id);
        if (courseOp.isPresent()) {
            return courseOp.get();
        }
        throw new ServiceException(MSG_COURSE_NOT_FOUND, NOT_FOUND);
    }

    public Course save(CourseDTO dto) {
        validateNotNull(dto);
        dto.setStatus(StatusEnum.PENDING.name());
        Course course = Course.mapper(dto);
        course.setUser(userService.getUserLogger());
        validateDuplicate(course);
        course.setInstituition(instituitionService.getById(dto.getIdInstituition()));
        course.setPriority(5);
        course.setFinished(Boolean.FALSE);
        return courseRepository.save(course);
    }

    private void validateDuplicate(Course course) {
        Course courseDuplicated = courseRepository.findByName(course.getName());
        if (nonNull(courseDuplicated) && courseDuplicated.equals(course)
            && !courseDuplicated.getId().equals(course.getId())) {
            throw new ServiceException(MSG_COURSE_DOUBLE);
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
        if (notation < 7) {
            course.setStatus(StatusEnum.FAIL);
        } else {
            course.setFinished(Boolean.TRUE);
            course.setStatus(StatusEnum.FINISH);
            course.setStartDate(new Date());
        }
        return courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    private void validateNotNull(CourseDTO course) {
        if (GeneralUtil.stringNullOrEmpty(course.getName())) {
            throw new ServiceException(MSG_FIELDS_NOT_FILLED);
        }
        if (GeneralUtil.stringNullOrEmpty(course.getDescription())) {
            throw new ServiceException(MSG_FIELDS_NOT_FILLED);
        }
        if (isNull(course.getCategory())) {
            throw new ServiceException(MSG_FIELDS_NOT_FILLED);
        }
        if (isNull(course.getIdInstituition())) {
            throw new ServiceException(MSG_FIELDS_NOT_FILLED);
        }
    }

}
