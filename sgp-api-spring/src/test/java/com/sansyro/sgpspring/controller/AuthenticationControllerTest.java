package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_CHECKER_CODE_INVALID;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_EMAIL_INVALID;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_USER_INVALID;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_USER_NOT_FOUND;
import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.util.GeneralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class AuthenticationControllerTest extends GenericControllerTest {

    private User userAuth;

    private User userBuild;

    @BeforeEach
    void setUp() throws CloneNotSupportedException, UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setFunctionalities(new HashSet<>());
        userBuild = user.clone();
        userAuth = userBuild.clone();
        userAuth.setPassword(SecurityUtil.cryptPassword(userAuth.getPassword() + userAuth.getUserHashCode()));
    }

    @Test
    void loginTest() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        when(userRepository.save(any())).thenReturn(userAuth);
        mockMvc.perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.id").value(userAuth.getId()))
            .andExpect(jsonPath("$.user.email").value(userAuth.getEmail()));

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        mockMvc.perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_INVALID.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_INVALID.getCode()));

        userAuth.setPassword(PASSWORD_DEFAULT);
        userBuild.setPassword(PASSWORD_DEFAULT);
        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        mockMvc.perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.id").value(userAuth.getId()))
            .andExpect(jsonPath("$.user.email").value(userAuth.getEmail()));

        when(userRepository.findByEmail(anyString())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void logoutTest() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userAuth));
        mockMvc.perform(
                put("/auth/logout/{id}", ID))
            .andExpect(status().isOk());

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(
                put("/auth/logout/{id}", ID))
            .andExpect(status().isOk());

        when(userRepository.findById(anyLong())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                put("/auth/logout/{id}", ID))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void registerTest() throws Exception {
        when(userRepository.save(any())).thenReturn(userAuth);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isOk());

        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(User.builder().name(userAuth.getName()).build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(
                        User.builder().name(userAuth.getName())
                            .email(userAuth.getName()).build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(MSG_EMAIL_INVALID.getCode()));

        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(
                        User.builder().name(userAuth.getName())
                            .email(userAuth.getEmail()).build())))
            .andExpect(status().isOk());

        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(
                        User.builder().name(userAuth.getName())
                            .email(userAuth.getEmail()).build())))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void activeNotFoundTest() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        mockMvc.perform(
                put("/auth/active")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_NOT_FOUND.getCode()));
    }

    @Test
    void activeExceptionTest() throws Exception {
        userAuth.setCheckerCode(RandomStringUtils.randomAlphanumeric(10));
        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        mockMvc.perform(
                put("/auth/active")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_CHECKER_CODE_INVALID.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_CHECKER_CODE_INVALID.getCode()));

        userBuild.setCheckerCode(userAuth.getCheckerCode());
        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                put("/auth/active")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void resetCheckerCodeTest() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        mockMvc.perform(
                put("/auth/reset-checker-code")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_INVALID.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_INVALID.getCode()));

        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        when(userRepository.save(any())).thenReturn(userAuth);
        mockMvc.perform(
                put("/auth/reset-checker-code")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isOk());

        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                put("/auth/reset-checker-code")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void changePasswordTest() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(userBuild);
        when(userRepository.save(any())).thenReturn(userAuth);
        String password = "" + userBuild.getPassword();
        mockMvc.perform(
                put("/auth/change-password/{oldPassword}", password)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_INVALID.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_INVALID.getCode()));

        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        mockMvc.perform(
                put("/auth/change-password/{oldPassword}", password)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isOk());

        userBuild.setPassword(null);
        mockMvc.perform(
                put("/auth/change-password/{oldPassword}", password)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        userBuild.setPassword(password);
        userAuth.setPassword(SecurityUtil.cryptPassword(password + userAuth.getUserHashCode()));
        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                put("/auth/change-password/{oldPassword}", password)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void updateTokenTest() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        when(userRepository.save(any())).thenReturn(userAuth);
        mockMvc.perform(
                post("/auth/update-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isOk());
    }

    @Test
    void updateTokenExceptionTest() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        mockMvc.perform(
                post("/auth/update-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_INVALID.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_INVALID.getCode()));

        when(userRepository.findByEmail(anyString())).thenReturn(userAuth);
        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                post("/auth/update-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GeneralUtil.generatedStringObject(userBuild)))
            .andExpect(status().isInternalServerError());
    }

}
