package com.sansyro.sgpspring.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.security.service.AuthenticationService;
import com.sansyro.sgpspring.security.service.TokenService;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class GenericControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AuthenticationService authenticationService;

    @Autowired
    protected TokenService tokenService;

    @MockBean
    protected UserRepository userRepository;

    protected final Long ID = 9999999l;

    protected User user = User.builder().id(ID).name(RandomStringUtils.randomAlphabetic(50))
        .email(RandomStringUtils.randomAlphabetic(10) + "@" + RandomStringUtils.randomAlphabetic(6) + ".com")
        .password(RandomStringUtils.randomAlphabetic(10)).userHashCode(RandomStringUtils.randomAlphabetic(10))
        .functionalities(Set.of(FunctionalityEnum.HOME, FunctionalityEnum.COURSE, FunctionalityEnum.INSTITUITION,
            FunctionalityEnum.LOTTERY, FunctionalityEnum.USER ))
        .startView("home").flActive(Boolean.TRUE).build();

}


