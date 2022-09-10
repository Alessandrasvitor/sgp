package com.sansyro.sgpspring.security;

import com.sansyro.sgpspring.repository.UserRepository;
import com.sansyro.sgpspring.service.DetailsService;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfigiguration extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    @Autowired
    private DetailsService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTFilter filter;

    //Ignora as urls livres de autenticação
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/api/**")
                .antMatchers(HttpMethod.POST,  "/login", "/register");
    }

    //Consulta o User no banco
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(SecurityUtil.bCryptPasswordEncoder());
    }

    //Seguranças do spring
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable().authorizeHttpRequests().antMatchers("/").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //redireciona quando deslogado
                .anyRequest().authenticated().and().logout().logoutUrl("/index")
                //mapeamento do logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //filtro das requisições no login
                .and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtApiAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


}
