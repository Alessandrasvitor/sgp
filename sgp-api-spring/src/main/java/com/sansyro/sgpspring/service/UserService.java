package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.util.GeralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private String PASSWORD_DEFAULT = "123456";

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
        user.setStartView(FunctionalityEnum.HOME.getPage());
        validateUserNull(user);
        validateUserDuplicate(user.getEmail());
        user.setUserHashCode(GeralUtil.getNewHashCode());
        user.setFunctionalities(new HashSet<>());
        user.getFunctionalities().add(FunctionalityEnum.HOME);
        user.setPassword(validatePassword(user.getPassword(), user.getUserHashCode()));
        return userRepository.save(user);
    }

    public User update(Long id, UserRequest request) {
        validateUserNull(request.mapperEntity());
        User user = getById(id);
        user.setStartView(request.getStartView());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    public User updateFunctionalities(Long id, UserRequest request) {
        User user = getById(id);
        user.setFunctionalities(request.getFunctionalities());
        return userRepository.save(user);
    }

    public String validatePassword(String password, String hash) {
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

    public void validateUserNull(User user) {
        if(GeralUtil.stringNullOrEmpty(user.getName())){
            throw new ServiceException("Nome do usuário é obrigatório");
        }
        if(GeralUtil.stringNullOrEmpty(user.getEmail())){
            throw new ServiceException("Email do usuário é obrigatório");
        }
        if(GeralUtil.stringNullOrEmpty(user.getStartView())){
            throw new ServiceException("Página inicial do usuário é obrigatória");
        }
    }

    public void resetPassword(Long id) {
        User user = getById(id);
        user.setPassword(PASSWORD_DEFAULT);
        userRepository.save(user);
    }

}
