package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.util.GeneralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.sansyro.sgpspring.constants.StringConstaint.*;
import static java.util.Objects.isNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> list() {
        return (List<User>) userRepository.findAll(Sort.by(Sort.Direction.ASC, NAME));
    }

    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getById(Long id) {
        Optional<User> userOp = userRepository.findById(id);
        if(userOp.isPresent()) {
            return userOp.get();
        }
        throw new ServiceException(MSG_USER_NOT_FOUND);
    }

    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new ServiceException(MSG_USER_NOT_FOUND);
        }
        return user;
    }

    public User save(User user) {
        user.setStartView(FunctionalityEnum.HOME.getPage());
        validateUserNull(user);
        validateUserDuplicate(user.getEmail());
        user.setUserHashCode(GeneralUtil.getNewHashCode());
        user.setFunctionalities(new HashSet<>());
        user.getFunctionalities().add(FunctionalityEnum.HOME);
        user.setPassword(PASSWORD_DEFAULT);
        return userRepository.save(user);
    }

    public User update(Long id, UserDTO request) {
        validateUserNull(User.mapper(request));
        User user = getById(id);
        user.setStartView(request.getStartView());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    public User updateFunctionalities(Long id, UserDTO request) {
        User user = getById(id);
        user.setFunctionalities(request.getFunctionalities());
        return userRepository.save(user);
    }

    public String validatePassword(String password, String hash) {
        if(GeneralUtil.stringNullOrEmpty(password)){
            throw new ServiceException("A Senha do usuário é obrigatória");
        }
        if(PASSWORD_DEFAULT.equals(password)){
            throw new ServiceException("A Senha não pode ser igual a: "+PASSWORD_DEFAULT);
        }
        return  SecurityUtil.bCryptPasswordEncoder().encode(password + hash);
    }

    private void validateUserDuplicate(String email) {
        if(userRepository.findByEmail(email) != null) {
            throw new ServiceException("Email já cadastrado na base");
        }
    }

    public void validateUserNull(User user) {
        if(isNull(user)) {
            throw new ServiceException("Campos obrigatórios não preenchidos");
        }
        if(GeneralUtil.stringNullOrEmpty(user.getName())){
            throw new ServiceException("Nome do usuário é obrigatório");
        }
        if(GeneralUtil.stringNullOrEmpty(user.getEmail())){
            throw new ServiceException("Email do usuário é obrigatório");
        }
        if(GeneralUtil.stringNullOrEmpty(user.getStartView())){
            throw new ServiceException("Página inicial do usuário é obrigatória");
        }
    }

    public void resetPassword(Long id) {
        User user = getById(id);
        user.setPassword(PASSWORD_DEFAULT);
        userRepository.save(user);
    }

    public User getUserLogger() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User)principal);
        } else {
            return null;
        }
    }

}
