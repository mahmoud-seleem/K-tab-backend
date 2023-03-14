package com.example.Backend;

import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfigurations.class, args);
	}

}
