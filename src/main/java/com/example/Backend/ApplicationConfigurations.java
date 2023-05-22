package com.example.Backend;

import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3Utils;
import com.example.Backend.s3Connection.S3fileSystem;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApplicationConfigurations {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.
//                        addMapping("/author/**")
//                        .allowedOrigins("http://localhost:8080");
//                registry.addMapping("/student/**")
//                        .allowedOrigins("http://localhost:8080");
//                registry.addMapping("/api/**")
//                        .allowedOrigins("http://localhost:8080");
//                registry.addMapping("/settings/**")
//                        .allowedOrigins("http://localhost:8080");
//                registry.addMapping("/usertype/**")
//                        .allowedOrigins("http://localhost:8080");
//            }
//        };
//    }
}
