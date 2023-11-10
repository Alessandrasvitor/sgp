package com.sansyro.sgpspring.security.filter;

import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.security.service.TokenService;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenAuthenticationFilterTest {

    @InjectMocks
    private TokenAuthenticationFilter filter;
    @Mock
    private UserRepository repository;
    @Mock
    private TokenService tokenService;

    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain filterChain;

    final String tokenValid = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJJUlMiLCJzdWIiOiI5OTk5OTk5IiwiaWF0IjoxNjk5NTc1MzMyLCJleHAiOjE2OTk1ODI1MzJ9.dTtNwh1cfjOiZvnrghkBAmqPTukNY8zl33ZJQxAiCkI";

    User user;

    @BeforeEach
    void setUp() {
        user = UserBuild.getBuild();
        user.setPassword(PASSWORD_DEFAULT);
        request = mock(HttpServletRequestWrapper.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void doFilterInternalNotUserTest() throws Exception {
        when(request.getHeader(anyString())).thenReturn(tokenValid);
        lenient().when(tokenService.isTokenValid(anyString())).thenReturn(Boolean.TRUE);
        lenient().when(tokenService.getTokenId(anyString())).thenReturn(1l);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        filter.doFilterInternal(request, response, filterChain);
        verify(request, times(1)).getHeader(anyString());
        verify(tokenService, times(1)).isTokenValid(anyString());
        verify(tokenService, times(1)).getTokenId(anyString());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void doFilterInternalTokenInvalidTest() throws Exception {
        when(request.getHeader(anyString())).thenReturn(RandomStringUtils.randomAlphanumeric(10));
        lenient().when(tokenService.isTokenValid(any())).thenCallRealMethod();
        filter.doFilterInternal(request, response, filterChain);
        verify(request, times(1)).getHeader(anyString());
        verify(tokenService, times(1)).isTokenValid(any());
    }

    @Test
    void doFilterInternalNotTokenTest() throws Exception {
        when(request.getHeader(anyString())).thenReturn(null);
        lenient().when(tokenService.isTokenValid(any())).thenCallRealMethod();
        filter.doFilterInternal(request, response, filterChain);
        verify(request, times(1)).getHeader(anyString());
        verify(tokenService, times(1)).isTokenValid(any());
    }

}
