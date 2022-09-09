package com.sansyro.sgpspring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SystemControllerTest {

    @InjectMocks
    private SystemController controller;

    @Test
    void listTest() {
        ResponseEntity response = controller.statusApi();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
