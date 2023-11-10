package com.sansyro.sgpspring.security.service;

import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;
import static java.util.Objects.isNull;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.constants.MessageEnum;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.exception.ForbiddenException;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.service.UserService;
import com.sansyro.sgpspring.util.GeneralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService service;
    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByEmail(username);
        if (isNull(user)) {
            throw new ForbiddenException(MessageEnum.MSG_USER_INVALID);
        }
        return user;
    }

    public User login(User userRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = (User) loadUserByUsername(userRequest.getEmail());

        if (user.getPassword().equals(PASSWORD_DEFAULT) && user.getPassword().equals(userRequest.getPassword())) {
            return returnUser(user);
        }
        validPassword(user.getPassword(), user.getUserHashCode(), userRequest.getPassword());
        return returnUser(user);
    }

    private void validPassword(String userPassword, String userHashCode, String requestPassword)
        throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (!SecurityUtil.cryptPassword(requestPassword + userHashCode).equals(userPassword)) {
            throw new ForbiddenException(MessageEnum.MSG_USER_INVALID);
        }
    }

    private User returnUser(User user) {
        user.setToken(tokenService.generateToken(user));
        return repository.save(user);
    }

    public void logout(Long id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setToken(null);
            repository.save(user);
        }
    }

    private User create(User userRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = service.save(userRequest);
        user.setPassword(service.validatePassword(user.getPassword(), user.getUserHashCode()));
        return repository.save(user);
    }

    public User register(User userRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = create(userRequest);
        service.sendEmailCreate(user);
        return user;
    }

    public User activateUser(User userRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = repository.findByEmail(userRequest.getEmail());
        if (isNull(user)) {
            throw new ServiceException(MessageEnum.MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        validPassword(user.getPassword(), user.getUserHashCode(), userRequest.getPassword());
        if (!user.getCheckerCode().equalsIgnoreCase(userRequest.getCheckerCode())) {
            throw new ServiceException(MessageEnum.MSG_CHECKER_CODE_INVALID);
        }
        user.getFunctionalities().add(FunctionalityEnum.COURSE);
        user.setToken(tokenService.generateToken(user));
        user.setFlActive(Boolean.TRUE);
        return repository.save(user);
    }

    private User updateCheckerCode(User userRequest) {
        User user = (User) loadUserByUsername(userRequest.getEmail());
        user.setCheckerCode(GeneralUtil.getNewCode().toUpperCase());
        return repository.save(user);
    }

    public void resetCheckerCode(User userRequest) {
        User user = updateCheckerCode(userRequest);
        service.sendEmailCreate(user);
    }

    public User updatePassword(String oldPassword, User request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = (User) loadUserByUsername(request.getEmail());
        validOldPassword(oldPassword, user);
        user.setUserHashCode(GeneralUtil.getNewCode());
        user.setPassword(service.validatePassword(request.getPassword(), user.getUserHashCode()));
        user.setFlActive(Boolean.FALSE);
        user.setToken(null);
        return repository.save(user);
    }

    private void validOldPassword(String oldPassword, User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (PASSWORD_DEFAULT.equals(oldPassword) && PASSWORD_DEFAULT.equals(user.getPassword())) {
            return;
        }
        validPassword(user.getPassword(), user.getUserHashCode(), oldPassword);
    }

    public User updateToken(User request) {
        User user = (User) loadUserByUsername(request.getEmail());
        user.setToken(tokenService.generateToken(user));
        return repository.save(user);
    }

}
