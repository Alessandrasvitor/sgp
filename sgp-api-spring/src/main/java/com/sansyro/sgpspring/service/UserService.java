package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.util.GeralUtil;
import com.sansyro.sgpspring.util.SegurancaUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public void save(User user) {
        validateUserNull(user);
        validateUserDuplicate(user.getEmail());
        user.setUserHashCode(getNewHashCode());
        user.setPassword(validatePassword(user.getPassword(), user.getUserHashCode()));
        userRepository.save(user);
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
        return SegurancaUtil.criptografarSHA256(password+hash);
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

}
