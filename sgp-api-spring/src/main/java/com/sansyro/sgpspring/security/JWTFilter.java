package com.sansyro.sgpspring.security;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.service.DetailsService;
import com.sansyro.sgpspring.service.UserService;
import com.sansyro.sgpspring.util.GeralUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class JWTFilter {

    private static String SECRET_KEY = "h+^I6%yM2@#<hzhFC>|[r";

    @Autowired
    private DetailsService userDetailsService;

    private static long EXPIRATION_TOKEN = 86400000;

    private static String TOKEN_PREFIXE = "Bearer";
    private static String HEADER = "Authorization";

    public static String getToken(String email) throws IOException {
        String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();

        String token = TOKEN_PREFIXE + " " + JWT;
        return "{\"Autorization\": \"" + token + "\"}";

    }

    public void addAuthentication(HttpServletResponse response, String email) throws IOException {

        String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();

        String token = TOKEN_PREFIXE + " " + JWT;
        response.addHeader(HEADER, token);
        response.getWriter().write("{\"Autorization\": \"" + token + "\"}");

    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HEADER);
        if(!GeralUtil.stringNullOrEmpty(token)) {
            String tokenClean = token.replace(TOKEN_PREFIXE, "").trim();
            String email = Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(tokenClean)
                    .getBody().getSubject();
            if(email != null) {
                User user = Context.getApplicationContext().getBean(UserService.class).getByEmail(email);
                return user.mapperAuthentication();
            }
        }

        corsRelease(response);
        return null;

    }

    private void corsRelease(HttpServletResponse response) {
        if(response.getHeader("Access-Control-Allow-Origin") == null) {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
        if(response.getHeader("Access-Control-Allow-Headers") == null) {
            response.setHeader("Access-Control-Allow-Headers", "*");
        }
        if(response.getHeader("Access-Control-Request-Headers") == null) {
            response.setHeader("Access-Control-Request-Headers", "*");
        }
        if(response.getHeader("Access-Control-Allow-Methods") == null) {
            response.setHeader("Access-Control-Allow-Methods", "*");
        }
    }

}
