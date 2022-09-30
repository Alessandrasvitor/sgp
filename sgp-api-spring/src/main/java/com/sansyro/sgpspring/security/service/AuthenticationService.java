package com.sansyro.sgpspring.security.service;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User login(UserRequest userRequest) throws UsernameNotFoundException {
        User user = (User) loadUserByUsername(userRequest.getEmail());

        if(user.getPassword().equals("123456") && user.getPassword().equals(userRequest.getPassword())) {
            return returnUser(user);
        }
        StringBuilder password = new StringBuilder(userRequest.getPassword()).append(user.getUserHashCode());
        String passwordCripto = SecurityUtil.bCryptPasswordEncoder().encode(password.toString());
        if(SecurityUtil.bCryptPasswordEncoder().matches(passwordCripto, user.getPassword())) {
            throw new UsernameNotFoundException("Usuário ou senha não encontrado");
        }
        return returnUser(user);
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

}
