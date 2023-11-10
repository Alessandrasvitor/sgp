package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_COURSE_DOUBLE;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_COURSE_NOT_FOUND;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.build.InstituitionBuild;
import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import com.sansyro.sgpspring.repository.CourseRepository;
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
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CourseControllerTest extends GenericControllerTest {

    @MockBean
    private CourseRepository repository;

    @MockBean
    private InstituitionRepository instituitionRepository;

    private CourseDTO courseDTOBuild;

    private Course courseBuild;

    @BeforeEach
    void setUp() {
        user.setToken(tokenService.generateToken(User.builder().id(user.getId()).build()));
        courseBuild = CourseBuild.getBuild();
        courseBuild.setUser(user);
        courseDTOBuild = CourseDTO.mapper(courseBuild);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    }

    @Test
    void listTest() throws Exception {
        when(repository.list(anyLong())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/course/all")
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        when(repository.list(anyLong(), (Pageable) any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("size", "3");
        mockMvc.perform(get("/course")
                .header("Authorization", "Bearer " + user.getToken())
                .params(params))
            .andExpect(status().isOk());
    }

    @Test
    void getByIdTest() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.of(courseBuild));
        mockMvc.perform(get("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_COURSE_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_COURSE_NOT_FOUND.getCode()));

        when(repository.findById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void saveTest() throws Exception {
        CourseDTO dto = CourseDTO.builder().build();
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        dto.setName(RandomStringUtils.randomAlphabetic(20));
        dto.setDescription(RandomStringUtils.randomAlphabetic(50));
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        Course clone = courseBuild.clone();
        clone.setId(null);
        when(repository.findByName(anyString())).thenReturn(courseBuild);
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(CourseDTO.mapper(clone))))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_COURSE_DOUBLE.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_COURSE_DOUBLE.getCode()));

        clone.setName(RandomStringUtils.randomAlphabetic(10));
        when(instituitionRepository.findById(anyLong())).thenReturn(Optional.of(InstituitionBuild.getBuild()));
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(CourseDTO.mapper(clone))))
            .andExpect(status().isCreated());

        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(CourseDTO.mapper(clone))))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void updateTest() throws Exception {
        CourseDTO dto = CourseDTO.builder().name(RandomStringUtils.randomAlphabetic(20)).build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(courseBuild));
        mockMvc.perform(put("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        dto.setDescription(RandomStringUtils.randomAlphabetic(50));
        dto.setCategory(CategoryEnum.INFORMATICA.name());
        mockMvc.perform(put("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        when(repository.findByName(anyString())).thenReturn(courseBuild);
        when(instituitionRepository.findById(anyLong())).thenReturn(Optional.of(InstituitionBuild.getBuild()));
        mockMvc.perform(put("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(courseDTOBuild)))
            .andExpect(status().isOk());

        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(put("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(courseDTOBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void startTest() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(patch("/course/start/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_COURSE_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_COURSE_NOT_FOUND.getCode()));

        when(repository.findById(anyLong())).thenReturn(Optional.of(courseBuild));
        mockMvc.perform(patch("/course/start/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(patch("/course/start/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void finishTest() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(patch("/course/finish/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(courseDTOBuild)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_COURSE_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_COURSE_NOT_FOUND.getCode()));

        Course clone = courseBuild.clone();
        clone.setNotation(7.6f);
        when(repository.findById(anyLong())).thenReturn(Optional.of(courseBuild));
        mockMvc.perform(patch("/course/finish/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(CourseDTO.mapper(clone))))
            .andExpect(status().isOk());

        clone.setNotation(3.9f);
        when(repository.findById(anyLong())).thenReturn(Optional.of(courseBuild));
        mockMvc.perform(patch("/course/finish/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(CourseDTO.mapper(clone))))
            .andExpect(status().isOk());

        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(patch("/course/finish/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(courseDTOBuild)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteTest() throws Exception {
        doNothing().when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_COURSE_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_COURSE_NOT_FOUND.getCode()));

        doThrow(RuntimeException.class).when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/course/{id}", courseBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isInternalServerError());
    }

}
