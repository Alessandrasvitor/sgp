package com.sansyro.sgpspring.security.service;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.service.UserService;
import com.sansyro.sgpspring.util.GeralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sansyro.sgpspring.constants.StringConstaint.MSG_USER_INVALID;
import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService service;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException(MSG_USER_INVALID);
        }
        return user;
    }

    public User login(User userRequest) throws UsernameNotFoundException {
        User user = (User) loadUserByUsername(userRequest.getEmail());

        if(user.getPassword().equals(PASSWORD_DEFAULT) && user.getPassword().equals(userRequest.getPassword())) {
            return returnUser(user);
        }
        if(validPassword(user.getPassword(), user.getUserHashCode(), userRequest.getPassword())) {
            throw new UsernameNotFoundException(MSG_USER_INVALID);
        }
        return returnUser(user);
    }

    private Boolean validPassword(String UserPassword, String UserHashCode, String requestPassword) {
        StringBuilder password = new StringBuilder(requestPassword).append(UserHashCode);
        String passwordCripto = SecurityUtil.bCryptPasswordEncoder().encode(password.toString());
        return SecurityUtil.bCryptPasswordEncoder().matches(passwordCripto, UserPassword);
    }

    private User returnUser(User user) {
        user.setToken(tokenService.generateToken(user));
        repository.save(user);
        return user;
    }

    public void logout(Long id) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setToken(null);
            repository.save(user);
        }
    }

    public User register(User user) {
        user.setPassword(service.validatePassword(user.getPassword(), GeralUtil.getNewHashCode()));
        user.setToken(tokenService.generateToken(user));
        repository.save(user);
        return user;
    }

    public User updatePassword(User request) {
        User user = service.getByEmail(request.getEmail());

        if(validPassword(user.getPassword(), user.getUserHashCode(), request.getPassword())) {
            throw new UsernameNotFoundException(MSG_USER_INVALID);
        }
        user.setUserHashCode(GeralUtil.getNewHashCode());
        user.setPassword(service.validatePassword(request.getPassword(), user.getUserHashCode()));
        user.setToken(tokenService.generateToken(user));
        return repository.save(user);
    }

    public User updateToken(User request) {
        User user = service.getByEmail(request.getEmail());

        user.setToken(tokenService.generateToken(user));
        return repository.save(user);
    }

}
