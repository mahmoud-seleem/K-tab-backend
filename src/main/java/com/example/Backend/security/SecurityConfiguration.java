package com.example.Backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;
import static com.example.Backend.security.Permissions.*;
import static com.example.Backend.security.Roles.*;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers("/api/**")
                                .hasRole(ADMIN.name())
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
                .roles(ADMIN.name()).build();
        UserDetails mohamedDetails = User.builder()
                .username("mohamed")
                .password(passwordEncoder.encode("456"))
                .roles(STUDENT.name()).build();
        return new InMemoryUserDetailsManager(
                mahmoudDetails,
                mohamedDetails
        );
    }
}
