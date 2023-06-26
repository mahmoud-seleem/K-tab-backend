package com.example.Backend;

import com.example.Backend.Repository.*;
import com.example.Backend.security.PasswordUtils;
import com.example.Backend.validation.json.JsonSchemaValidatingArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import com.example.Backend.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableWebMvc
public class BackendApplication {

    @Value("${ALLOWED_DOMAINS}")
    private String allowedDomains;

	public static void main(String[] args) throws ClassNotFoundException {
		ApplicationContext context = SpringApplication.run(BackendApplication.class, args);
		AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
		StudentRepository studentRepository = context.getBean(StudentRepository.class);
		DisabilityRepository disabilityRepository = context.getBean(DisabilityRepository.class);
		AuthorSettingsRepository authorSettingsRepository = context.getBean(AuthorSettingsRepository.class);
		StudentSettingsRepository studentSettingsRepository = context.getBean(StudentSettingsRepository.class);
		PasswordUtils passwordUtils = new PasswordUtils();
		Utils utils = new Utils(
				passwordUtils.passwordEncoder(),
				studentRepository,
				authorRepository,
				disabilityRepository,
				studentSettingsRepository,
				authorSettingsRepository);
		utils.generateSomeUsers();
		utils.generateSomeDisabilities();
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedDomains.split(","))
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .maxAge(3600);
            }
        };
    }

//	@Autowired
//	private RequestMappingHandlerAdapter adapter;

//	@PostConstruct
//	public void prioritizeCustomArgumentMethodHandlers () {
//		List<HandlerMethodArgumentResolver> argumentResolvers =
//				new ArrayList<>(adapter.getArgumentResolvers ());
//		List<HandlerMethodArgumentResolver> customResolvers =
//				adapter.getCustomArgumentResolvers ();
//		argumentResolvers.removeAll (customResolvers);
//		argumentResolvers.addAll (0, customResolvers);
//		adapter.setArgumentResolvers (argumentResolvers);
//	}
}
