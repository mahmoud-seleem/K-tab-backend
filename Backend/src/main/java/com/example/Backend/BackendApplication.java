package com.example.Backend;

import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

import static com.example.Backend.s3Connection.S3DeleteInvalidFiles.deleteFilesFromS3;
import static com.example.Backend.s3Connection.S3fileSystem.createFileSystemLibrary;

public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfigurations.class, args);

//		System.out.println(createFileSystemLibrary("content-pwd-aat", UUID.randomUUID(), UUID.randomUUID()));

		String [] objectKeys = {"books/13365979-bf51-445f-81bd-e924e0857dee", "00a098e2-6f93-44d5-9f7d-5fac994078ad"};
		System.out.println(deleteFilesFromS3("content-pwd-aat", "books/13365979-bf51-445f-81bd-e924e0857dee/"));
	}

}
