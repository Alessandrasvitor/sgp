package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.StringConstaint.NAME;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('USER')")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(userService.list()
            .stream().map(UserDTO::mapper).toList());
    }

    @ResponseBody
    @GetMapping()
    public ResponseEntity list(@PageableDefault(sort = NAME,
        direction = Sort.Direction.ASC,
        size = 5) Pageable pageable) {
        return ResponseEntity.ok().body(userService.list(pageable).map(UserDTO::mapper));
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(UserDTO.mapper(userService.getById(id)));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity save(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.mapper(userService.create(user)));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserDTO user) {
        try {
            return ResponseEntity.ok().body(UserDTO.mapper(userService.update(id, user)));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PatchMapping("functionalities/{id}")
    public ResponseEntity updateFunctionalities(@PathVariable Long id, @RequestBody UserDTO user) {
        try {
            return ResponseEntity.ok().body(UserDTO.mapper(userService.updateFunctionalities(id, user)));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/password/{id}")
    public ResponseEntity getWithPassword(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.getById(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PatchMapping("/reset/{id}")
    public ResponseEntity resetPassword(@PathVariable Long id) {
        try {
            userService.resetPassword(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/info")
    public ResponseEntity getUserDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String details = "User: " + authentication.getPrincipal() + "<br/>" +
            "User Authorities: " + authentication.getAuthorities() + "<br/>" +
            "Detalhes: " + authentication.getDetails();
        return ResponseEntity.ok().body(details);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            userService.disable(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
