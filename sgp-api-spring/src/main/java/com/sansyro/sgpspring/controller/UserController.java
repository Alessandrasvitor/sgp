package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping()
    public ResponseEntity list() {
        return ResponseEntity.ok().body(userService.list().stream().map(User::mapperDTP).toList());
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.getById(id).mapperDTP());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity save(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity.ok().body(userService.update(id, userRequest).mapperDTP());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PatchMapping("{id}")
    public ResponseEntity updatePassword(@PathVariable Long id, @RequestBody String senha) {
        try {
            return ResponseEntity.ok().body(userService.updatePassword(id, senha).mapperDTP());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/password/{id}")
    public ResponseEntity getWithPassword(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.getById(id));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/reset/{id}")
    public ResponseEntity resetPassword(@PathVariable Long id) {
        try {
            userService.resetPassword(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/info")
    public ResponseEntity getUserDetails() {
        try {
            String details = "User: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal() + "<br/>" +
                    "User Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities() + "<br/>" +
                    "Detalhes: " + SecurityContextHolder.getContext().getAuthentication().getDetails();
            return ResponseEntity.ok().body(details);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
