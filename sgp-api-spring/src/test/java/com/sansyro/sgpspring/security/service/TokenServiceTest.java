package com.sansyro.sgpspring.security.service;

import static org.junit.Assert.assertFalse;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService service;

    @Test
    void isTokenValidExceptionTest() {
        assertFalse(service.isTokenValid(RandomStringUtils.randomAlphanumeric(20)));
    }

}
