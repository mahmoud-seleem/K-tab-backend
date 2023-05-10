package com.example.Backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static com.example.Backend.security.Role.*;
import static com.example.Backend.security.Permission.*;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        requests -> requests
//                                .requestMatchers("/api/**")
//                                .hasAuthority(CHAPTER_WRITE.getName())
                .anyRequest()
                .authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails mahmoudDetails = User.builder()
                .username("mahmoud")
                .password(passwordEncoder.encode("123"))
                .authorities(ADMIN.getGrantedAuthorities()).build();
        UserDetails mohamedDetails = User.builder()
                .username("mohamed")
                .password(passwordEncoder.encode("456"))
                .authorities(STUDENT.getGrantedAuthorities()).build();
        UserDetails ahmedDetails = User.builder()
                .username("ahmed")
                .password(passwordEncoder.encode("789"))
                .authorities(STUDENT.getGrantedAuthorities()).build();
        return new InMemoryUserDetailsManager(
                mahmoudDetails,
                mohamedDetails,
                ahmedDetails
        );
    }
}
