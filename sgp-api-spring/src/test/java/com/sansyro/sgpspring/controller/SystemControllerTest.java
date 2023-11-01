package com.sansyro.sgpspring.controller;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SystemControllerTest extends GenericControllerTest {

    @Test
    void listTest() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk());
    }

}
