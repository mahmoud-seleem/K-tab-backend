package com.example.Backend;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import com.example.Backend.security.PasswordUtils;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BackendApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ApplicationConfigurations.class, args);
		AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
		StudentRepository studentRepository = context.getBean(StudentRepository.class);
		PasswordUtils passwordUtils = new PasswordUtils();
		Utils utils = new Utils(
				passwordUtils.passwordEncoder(),
				studentRepository,
				authorRepository);
  		utils.generateSomeUsers();
	}

}
