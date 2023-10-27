package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.TokenResponse;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.security.service.AuthenticationService;
import com.sansyro.sgpspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.sansyro.sgpspring.constants.StringConstaint.BEARER;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated User request){
        try {
            User user = authenticationService.login(request);
            return ResponseEntity.ok(TokenResponse.builder().user(UserDTO.mapper(user)).type(BEARER).token(user.getToken()).build());
        } catch (ServiceException | UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable Long id){
        try {
            authenticationService.logout(id);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated User request){
        try {
            return ResponseEntity.ok(UserDTO.mapper(authenticationService.register(request)));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/atctive")
    public ResponseEntity active(@RequestBody @Validated User request){
        try {
            User user = authenticationService.activateUser(request);
            return ResponseEntity.ok(TokenResponse.builder().user(UserDTO.mapper(user)).type(BEARER).token(user.getToken()).build());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/reset-checker-code")
    public ResponseEntity resetCheckerCode(@RequestBody @Validated User request){
        try {
            authenticationService.resetCheckerCode(request);
            return ResponseEntity.ok().build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/change-password/{oldPassword}")
    public ResponseEntity changePassword(@PathVariable String oldPassword, @RequestBody @Validated User request){
        try {
            return ResponseEntity.ok(UserDTO.mapper(authenticationService.updatePassword(oldPassword, request)));
        } catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update-token")
    public ResponseEntity updateToken(@RequestBody @Validated User request){
        try {
            User user = authenticationService.updateToken(request);
            return ResponseEntity.ok(TokenResponse.builder().user(UserDTO.mapper(user)).type(BEARER).token(user.getToken()).build());
        } catch (ServiceException | UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}