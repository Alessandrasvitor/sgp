package com.sansyro.sgpspring.security.filter;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.security.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.sansyro.sgpspring.constants.StringConstaint.AUTHORIZATION;
import static com.sansyro.sgpspring.constants.StringConstaint.BEARER;
import static java.util.Objects.isNull;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository repository;

    public TokenAuthenticationFilter(TokenService tokenService, UserRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenFromHeader = getTokenFromHeader(request);
        boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
        if(tokenValid) {
            this.authenticate(tokenFromHeader);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(String tokenFromHeader) {
        Long id = tokenService.getTokenId(tokenFromHeader);
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if(isNull(token) || !token.startsWith(BEARER)) {
            return null;
        }
        return token.substring(7);
    }

}
