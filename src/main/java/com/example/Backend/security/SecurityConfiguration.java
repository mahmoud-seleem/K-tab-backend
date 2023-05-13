package com.example.Backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
import static com.example.Backend.security.Role.*;
import static com.example.Backend.security.Permission.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers(
                                        "/api/security/login/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
//                .httpBasic(withDefaults());
        http.authenticationProvider(AuthenticationProvider());
//                http.addFilterBefore(
//                        authenticationJwtTokenFilter(),
//                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
////    }
//    @Bean
//    public DataSource dataSource(){
//        new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.)
//    }
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
//        return authenticationManagerBuilder.build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider AuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserDetailsService);
        return provider;
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails mahmoudDetails = User.builder()
//                .username("mahmoud")
//                .password(passwordEncoder.encode("123"))
//                .authorities(ADMIN.getGrantedAuthorities()).build();
//        UserDetails mohamedDetails = User.builder()
//                .username("mohamed")
//                .password(passwordEncoder.encode("456"))
//                .authorities(STUDENT.getGrantedAuthorities()).build();
//        UserDetails ahmedDetails = User.builder()
//                .username("ahmed")
//                .password(passwordEncoder.encode("789"))
//                .authorities(STUDENT.getGrantedAuthorities()).build();
//        return new InMemoryUserDetailsManager(
//                mahmoudDetails,
//                mohamedDetails,
//                ahmedDetails
//        );
//    }
}
