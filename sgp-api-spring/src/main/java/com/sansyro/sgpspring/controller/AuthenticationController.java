package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.TokenResponse;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.security.service.AuthenticationService;
import com.sansyro.sgpspring.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity auth(@RequestBody @Validated UserRequest request){
        try {
            User user = authenticationService.login(request);
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(TokenResponse.builder().type("Bearer").token(token).build());
        } catch (ServiceException | UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}