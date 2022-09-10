package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.security.JWTFilter;
import com.sansyro.sgpspring.util.GeralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private String PASSWORD_DEFAULT = "bh1234";

    public List<User> list() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        Optional<User> userOp = userRepository.findById(id);
        if(userOp.isPresent()) {
            return userOp.get();
        }
        throw new ServiceException("Usuário não encontrado");
    }

    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new ServiceException("Usuário não encontrado");
        }
        return user;
    }

    public User save(User user) {
        validateUserNull(user);
        validateUserDuplicate(user.getEmail());
        user.setUserHashCode(getNewHashCode());
        user.setPassword(validatePassword(user.getPassword(), user.getUserHashCode()));
        return userRepository.save(user);
    }

    public User update(Long id, UserRequest user) {
        validateUserNull(user.mapperEntity());
        User userUpdate = getById(id);
        userUpdate.setName(user.getName());
        userUpdate.setEmail(user.getEmail());
        return userRepository.save(userUpdate);
    }

    public User updatePassword(Long id, String password) {
        User userUpdate = getById(id);
        userUpdate.setPassword(validatePassword(password,userUpdate.getUserHashCode()));
        return userRepository.save(userUpdate);
    }

    private String validatePassword(String password, String hash) {
        if(GeralUtil.stringNullOrEmpty(password)){
            throw new ServiceException("A Senha do usuário é obrigatória");
        }
        StringBuilder newPassword = new StringBuilder(password).append(hash);
        return  SecurityUtil.bCryptPasswordEncoder().encode(newPassword.toString());
    }

    private void validateUserDuplicate(String email) {
        if(userRepository.findByEmail(email) != null) {
            throw new ServiceException("Email já cadastrado na base");
        }
    }

    private void validateUserNull(User user) {
        if(GeralUtil.stringNullOrEmpty(user.getName())){
            throw new ServiceException("Nome do usuário é obrigatório");
        }
        if(GeralUtil.stringNullOrEmpty(user.getEmail())){
            throw new ServiceException("Email do usuário é obrigatório");
        }
    }

    private String getNewHashCode() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public String login(UserRequest userRequest) throws UsernameNotFoundException, IOException {
        User user = userRepository.findByEmail(userRequest.getEmail());
        if(user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        StringBuilder password = new StringBuilder(userRequest.getPassword()).append(user.getUserHashCode());
        String passwordCripto = SecurityUtil.bCryptPasswordEncoder().encode(password.toString());
        if(SecurityUtil.bCryptPasswordEncoder().matches(passwordCripto, user.getPassword())) {
            return null;
        }

        user.setToken(JWTFilter.getToken(user.getEmail()));
        userRepository.save(user);
        return user.getToken();
    }

    public void resetPassword(Long id) {
        User user = getById(id);
        StringBuilder password = new StringBuilder(PASSWORD_DEFAULT).append(user.getUserHashCode());
        user.setPassword(password.toString());
        userRepository.save(user);
    }

}
