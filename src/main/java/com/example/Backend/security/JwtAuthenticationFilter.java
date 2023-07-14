package com.example.Backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class
JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppUserDetailsService appUserDetailsService;

    //    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain) throws ServletException, IOException {
//        String jwtHeader = request.getHeader("Authorization");
//        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String jwtToken = jwtHeader.substring(7);
//        String userEmail = jwtService.extractEmail(jwtToken);
//        if (userEmail != null && SecurityContextHolder
//                .getContext().
//                getAuthentication() == null) {
//            UserDetails userDetails =
//                    appUserDetailsService.
//                            loadUserByUsername(userEmail);
//            if (jwtService.isTokenValid(jwtToken,userDetails)) {
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities());
//            authenticationToken.setDetails(
//                    new WebAuthenticationDetailsSource().buildDetails(request)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//        filterChain.doFilter(request,response);
//    }
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = jwtService.parseJwt(request);
            if (jwt != null) {
                String username = jwtService.getUserEmailFromJwtToken(jwt);
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);
                if(jwtService.validateJwtToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.info("Invalid JWT token" + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
