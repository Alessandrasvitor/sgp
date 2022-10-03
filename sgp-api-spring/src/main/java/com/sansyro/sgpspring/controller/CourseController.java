package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.dto.CourseRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.CourseService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('COURSE')")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ResponseBody
    @GetMapping()
    public ResponseEntity list() {
        return ResponseEntity.ok().body(courseService.list());
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(courseService.getById(id));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity save(@RequestBody CourseRequest course) {
        try {
            courseService.save(course);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody CourseRequest course) {
        try {
            return ResponseEntity.ok().body(courseService.update(id, course));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("finish/{id}")
    public ResponseEntity finish(@PathVariable Long id, @RequestBody CourseRequest course) {
        try {
            return ResponseEntity.ok().body(courseService.finish(id, course.getNotation()));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("start/{id}")
    public ResponseEntity start(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(courseService.start(id));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("{id}")
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
