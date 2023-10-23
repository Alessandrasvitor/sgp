package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.dto.CourseDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.CourseService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('COURSE')")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ResponseBody
    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(courseService.list()
                .stream().map(CourseDTO::mapper).toList());
    }

    @ResponseBody
    @GetMapping()
    public ResponseEntity list(@PageableDefault(sort = "name",
            direction = Sort.Direction.ASC,
            size = 5) Pageable pageable) {
        return ResponseEntity.ok().body(courseService.list(pageable).map(CourseDTO::mapper));
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(CourseDTO.mapper(courseService.getById(id)));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity save(@RequestBody CourseDTO course) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(CourseDTO.mapper(courseService.save(course)));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody CourseDTO course) {
        try {
            return ResponseEntity.ok().body(CourseDTO.mapper(courseService.update(id, course)));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/finish/{id}")
    public ResponseEntity finish(@PathVariable Long id, @RequestBody CourseDTO course) {
        try {
            return ResponseEntity.ok().body(CourseDTO.mapper(courseService.finish(id, course.getNotation())));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/start/{id}")
    public ResponseEntity start(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(CourseDTO.mapper(courseService.start(id)));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            courseService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
