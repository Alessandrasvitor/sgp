package com.sansyro.sgpspring.controller;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_LOTERY_NOT_FOUND;
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

import com.sansyro.sgpspring.build.LotteryBuild;
import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import com.sansyro.sgpspring.entity.Lottery;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.LotteryDTO;
import com.sansyro.sgpspring.repository.LotteryRepository;
import com.sansyro.sgpspring.util.GeneralUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class LotteryControllerTest extends GenericControllerTest {

    @MockBean
    private LotteryRepository repository;

    private Lottery lotteryBuild;

    @BeforeEach
    void setUp() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        user.setToken(tokenService.generateToken(User.builder().id(user.getId()).build()));
        lotteryBuild = LotteryBuild.getBuild();
        lotteryBuild.setId(user.getId());
    }

    @Test
    void listTest() throws Exception {
        when(repository.list(anyLong())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/lottery/all")
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        when(repository.list(anyLong(), (Pageable) any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("sort", "ASC");
        params.add("size", "3");
        mockMvc.perform(get("/lottery")
                .header("Authorization", "Bearer " + user.getToken())
                .params(params))
            .andExpect(status().isOk());
    }

    @Test
    void getByIdTest() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.of(lotteryBuild));
        mockMvc.perform(get("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_LOTERY_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_LOTERY_NOT_FOUND.getCode()));

        when(repository.findById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void saveTest() throws Exception {
        mockMvc.perform(post("/lottery")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(LotteryDTO.builder().build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        when(repository.save(any())).thenReturn(lotteryBuild);
        mockMvc.perform(post("/lottery")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(LotteryDTO.mapper(lotteryBuild))))
            .andExpect(status().isCreated());

        var lottery = lotteryBuild.clone();
        lottery.setLotteryDate(null);
        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/lottery")
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(LotteryDTO.mapper(lottery))))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void updateTest() throws Exception {
        mockMvc.perform(put("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(
                    LotteryDTO.builder().bet(RandomStringUtils.randomNumeric(12)).build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userMessage").value(MSG_FIELDS_NOT_FILLED.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_FIELDS_NOT_FILLED.getCode()));

        when(repository.findById(anyLong())).thenReturn(Optional.of(lotteryBuild));
        when(repository.save(any())).thenReturn(lotteryBuild);
        mockMvc.perform(put("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(LotteryDTO.mapper(lotteryBuild))))
            .andExpect(status().isOk());

        when(repository.save(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(put("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(GeneralUtil.generatedStringObject(LotteryDTO.mapper(lotteryBuild))))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void generateTest() throws Exception {
        when(repository.list(anyLong())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/lottery/generate/{typeLottery}",
                TypeLotteryEnum.values()[RandomUtils.nextInt(0, TypeLotteryEnum.values().length)].name())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());
    }

    @Test
    void deleteTest() throws Exception {
        doNothing().when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isOk());

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.userMessage").value(MSG_LOTERY_NOT_FOUND.getMessage()))
            .andExpect(jsonPath("$.code").value(MSG_LOTERY_NOT_FOUND.getCode()));

        doThrow(RuntimeException.class).when(repository).deleteById(anyLong());
        mockMvc.perform(delete("/lottery/{id}", lotteryBuild.getId())
                .header("Authorization", "Bearer " + user.getToken()))
            .andExpect(status().isInternalServerError());
    }

}
