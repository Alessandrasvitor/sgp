package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.security.JWTFilter;
import com.sansyro.sgpspring.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class AuthController {

    @Autowired
    private JWTFilter filter;

    @Autowired
    private UserService service;

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest request){
        try {
            return ResponseEntity.ok().body(service.login(request));
        } catch (ServiceException | AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário ou senha inválido");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @ResponseBody
//    @PostMapping("/register")
//    public Map<String, Object> registerHandler(@RequestBody User user){
//        user = service.save(user);
//        String token = filter.generateToken(user.getEmail());
//        return Collections.singletonMap("jwt-token", token);
//    }

}
