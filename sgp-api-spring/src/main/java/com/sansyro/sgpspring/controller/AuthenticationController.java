package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.StringConstaint.BEARER;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.TokenResponse;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.exception.ForbiddenException;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated User request) {
        try {
            User user = authenticationService.login(request);
            return ResponseEntity.ok(TokenResponse.builder().user(UserDTO.mapper(user)).type(BEARER).token(user.getToken()).build());
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable Long id) {
        try {
            authenticationService.logout(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated User request) {
        try {
            return ResponseEntity.ok(UserDTO.mapper(authenticationService.register(request)));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/atctive")
    public ResponseEntity active(@RequestBody @Validated User request) {
        try {
            User user = authenticationService.activateUser(request);
            return ResponseEntity.ok(TokenResponse.builder().user(UserDTO.mapper(user)).type(BEARER).token(user.getToken()).build());
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessageError());
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/reset-checker-code")
    public ResponseEntity resetCheckerCode(@RequestBody @Validated User request) {
        try {
            authenticationService.resetCheckerCode(request);
            return ResponseEntity.ok().build();
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/change-password/{oldPassword}")
    public ResponseEntity changePassword(@PathVariable String oldPassword, @RequestBody @Validated User request) {
        try {
            return ResponseEntity.ok(UserDTO.mapper(authenticationService.updatePassword(oldPassword, request)));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessageError());
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update-token")
    public ResponseEntity updateToken(@RequestBody @Validated User request) {
        try {
            User user = authenticationService.updateToken(request);
            return ResponseEntity.ok(TokenResponse.builder().user(UserDTO.mapper(user)).type(BEARER).token(user.getToken()).build());
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessageError());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}