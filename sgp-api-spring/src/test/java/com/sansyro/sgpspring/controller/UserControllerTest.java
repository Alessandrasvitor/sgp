package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_EMAIL_INVALID;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_USER_DOUBLE;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_USER_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.util.GeneralUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class UserControllerTest extends GenericControllerTest {

    private User userBuild;

    private UserDTO dtoBuild;

    @BeforeEach
    void setUp() throws CloneNotSupportedException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        user.setFunctionalities(Set.of(FunctionalityEnum.USER, FunctionalityEnum.HOME));
        user.setToken(tokenService.generateToken(User.builder().id(user.getId()).build()));
        userBuild = user.clone();
        dtoBuild = UserDTO.mapper(userBuild);
    }

    @Test
    void listTest() throws Exception {
        when(userRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/all")
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("size", "3");
        when(userRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + user.getToken())
                .params(params))
            .andExpect(status().isOk());
    }

    @Test
    void getTest() throws Exception {
        when(userRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());
    }

    @Test
    void saveTest() throws Exception {
        var userSaveError = User.builder().build();
        mockMvc.perform(post("/user")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(User.builder().build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        userSaveError.setName(RandomStringUtils.randomAlphabetic(20));
        userSaveError.setEmail(RandomStringUtils.randomAlphabetic(20));
        mockMvc.perform(post("/user")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(userSaveError)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(
                GeneralUtil.getMessageExeption(MSG_EMAIL_INVALID.getMessage(), userSaveError.getEmail())))
            .andExpect(jsonPath("$.code").value(MSG_EMAIL_INVALID.getCode()));

        when(userRepository.findByEmail(anyString())).thenReturn(userBuild);
        mockMvc.perform(post("/user")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(
                GeneralUtil.getMessageExeption(MSG_USER_DOUBLE.getMessage(), dtoBuild.getEmail())))
            .andExpect(jsonPath("$.code").value(MSG_USER_DOUBLE.getCode()));

        when(userRepository.save(any())).thenReturn(userBuild);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        mockMvc.perform(post("/user")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andDo(print())
            .andExpect(status().isCreated());

        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/user")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void updateTest() throws Exception {
        mockMvc.perform(put("/user/{id}", user.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(User.builder()
                    .name(RandomStringUtils.randomAlphabetic(20)).build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        when(userRepository.findByEmail(anyString())).thenReturn(userBuild);
        mockMvc.perform(put("/user/{id}", 1l)
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(
                GeneralUtil.getMessageExeption(MSG_USER_DOUBLE.getMessage(), dtoBuild.getEmail())))
            .andExpect(jsonPath("$.code").value(MSG_USER_DOUBLE.getCode()));

        when(userRepository.save(any())).thenReturn(userBuild);
        mockMvc.perform(put("/user/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andDo(print())
            .andExpect(status().isOk());

        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(put("/user/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void updateFunctionalitiesTest() throws Exception {
        when(userRepository.save(any())).thenReturn(userBuild);
        mockMvc.perform(patch("/user/functionalities/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isOk());

        when(userRepository.save(any())).thenThrow(new ServiceException(MSG_USER_NOT_FOUND, NOT_FOUND));
        mockMvc.perform(patch("/user/functionalities/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_NOT_FOUND.getCode()));
    }

    @Test
    void updateFunctionalitiesInternalServerErrorTest() throws Exception {
        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(patch("/user/functionalities/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void getWithPasswordTest() throws Exception {
        mockMvc.perform(get("/user/password/{id}", user.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());
    }

    @Test
    void resetPasswordTest() throws Exception {
        when(userRepository.save(any())).thenReturn(userBuild);
        mockMvc.perform(patch("/user/reset/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isOk());

        when(userRepository.save(any())).thenThrow(new ServiceException(MSG_USER_NOT_FOUND, NOT_FOUND));
        mockMvc.perform(patch("/user/reset/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_NOT_FOUND.getCode()));
    }

    @Test
    void resetPasswordInternalServerErrorTest() throws Exception {
        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(patch("/user/reset/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dtoBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void getUserDetailsTest() throws Exception {
        mockMvc.perform(get("/user/info")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void deleteTest() throws Exception {
        when(userRepository.save(any())).thenReturn(userBuild);
        mockMvc.perform(delete("/user/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        when(userRepository.save(any())).thenThrow(new ServiceException(MSG_USER_NOT_FOUND, NOT_FOUND));
        mockMvc.perform(delete("/user/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_USER_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_USER_NOT_FOUND.getCode()));
    }

    @Test
    void deleteInternalServerErrorTest() throws Exception {
        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(delete("/user/{id}", userBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

}
