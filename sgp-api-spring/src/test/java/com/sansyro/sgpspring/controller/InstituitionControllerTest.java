package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_INSTITUITION_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sansyro.sgpspring.build.InstituitionBuild;
import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.Instituition;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.repository.InstituitionRepository;
import com.sansyro.sgpspring.util.GeneralUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class InstituitionControllerTest extends GenericControllerTest {

    @MockBean
    private InstituitionRepository repository;

    private Instituition instituitionBuild;

    @BeforeEach
    void setUp() {
        user.setToken(tokenService.generateToken(User.builder().id(user.getId()).build()));
        instituitionBuild = InstituitionBuild.getBuild();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    }

    @Test
    void listTest() throws Exception {
        when(repository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/instituition/all")
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());
    }

    @Test
    void getByIdTest() throws Exception {
        when(repository.findAll((Pageable) any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("size", "3");

        mockMvc.perform(get("/instituition")
                .header("Authorization", "Bearer " + user.getToken())
                .params(params))
            .andExpect(status().isOk());
    }

    @Test
    void getByIdWithBadRequestTest() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.of(instituitionBuild));
        mockMvc.perform(get("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_INSTITUITION_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_INSTITUITION_NOT_FOUND.getCode()));

        when(repository.findById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void saveTest() throws Exception {
        mockMvc.perform(post("/instituition")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(Instituition.builder().build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        when(repository.save(any())).thenReturn(instituitionBuild);
        mockMvc.perform(post("/instituition")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(instituitionBuild)))
            .andExpect(status().isCreated());

        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/instituition")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(instituitionBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void updateTest() throws Exception {

        mockMvc.perform(put("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(Instituition.builder()
                    .name(RandomStringUtils.randomAlphabetic(50)).build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        when(repository.findById(anyLong())).thenReturn(Optional.of(instituitionBuild));
        when(repository.save(any())).thenReturn(instituitionBuild);
        mockMvc.perform(put("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(instituitionBuild)))
            .andExpect(status().isOk());

        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(put("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(instituitionBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteTest() throws Exception {
        doNothing().when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_INSTITUITION_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_INSTITUITION_NOT_FOUND.getCode()));

        doThrow(RuntimeException.class).when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/instituition/{id}", instituitionBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isInternalServerError());
    }

}
