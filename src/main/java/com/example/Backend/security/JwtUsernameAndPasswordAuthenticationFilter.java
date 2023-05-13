//package com.example.Backend.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.Date;
//
//@Component
//public class JwtUsernameAndPasswordAuthenticationFilter
//        extends UsernamePasswordAuthenticationFilter {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Override
//    public Authentication attemptAuthentication(
//            HttpServletRequest request,
//            HttpServletResponse response) throws AuthenticationException {
//
//
//        try {
//            AuthenticationRequest authenticationRequest = new ObjectMapper()
//                    .readValue(request.getInputStream(), AuthenticationRequest.class);
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            authenticationRequest.getEmail(),
//                            authenticationRequest.getPassword()
//                    );
//            Authentication authenticationResult =
//                    authenticationManager.authenticate(authentication);
//            return authenticationResult;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain chain,
//            Authentication authResult) throws IOException, ServletException {
//        String jwt = Jwts.builder()
//                .setSubject(authResult.getName())
//                .claim("authorities", authResult.getAuthorities())
//                .setIssuedAt(new Date())
//                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
//                .signWith(Keys.hmacShaKeyFor("secure".getBytes()))
//                .compact();
//        response.addHeader("Authorization","Bearer "+jwt);
//        super.successfulAuthentication(request, response, chain, authResult);
//    }
//}
