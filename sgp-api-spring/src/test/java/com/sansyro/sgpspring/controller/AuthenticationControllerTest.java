package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.service.UserService;
import com.sansyro.sgpspring.util.GeneralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest extends GenericControllerTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private final Long ID = 1L;

    private User user;

    private User userBuild;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() throws CloneNotSupportedException {
        userBuild = UserBuild.getBuild();
        user = userBuild.clone();
        user.setPassword(SecurityUtil.cryptPassword(user.getPassword()+user.getUserHashCode()));

    }

    @Test
    void loginTest() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GeneralUtil.generatedStringObject(userBuild)))
                .andExpect(status().isOk());

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(GeneralUtil.generatedStringObject(userBuild)))
                .andExpect(status().isBadRequest());

        when(userRepository.findByEmail(anyString())).thenReturn(userBuild);
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(GeneralUtil.generatedStringObject(userBuild)))
                .andExpect(status().isBadRequest());

        user.setPassword(PASSWORD_DEFAULT);
        userBuild.setPassword(PASSWORD_DEFAULT);
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(GeneralUtil.generatedStringObject(userBuild)))
                .andExpect(status().isOk());

        when(userRepository.findByEmail(anyString())).thenThrow(RuntimeException.class);
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(GeneralUtil.generatedStringObject(userBuild)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void logoutTest() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
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
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
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
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(GeneralUtil.generatedStringObject(User.builder().name(user.getName()).build())))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(GeneralUtil.generatedStringObject(
                                        User.builder().name(user.getName())
                                                .email(user.getName()).build())))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(GeneralUtil.generatedStringObject(
                                        User.builder().name(user.getName())
                                                .email(user.getEmail()).build())))
                .andExpect(status().isOk());

    }

}
