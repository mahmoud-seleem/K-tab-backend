package com.example.Backend.utils;

import com.example.Backend.Repository.*;
import com.example.Backend.model.Author;
import com.example.Backend.model.Disability;
import com.example.Backend.model.Student;
import com.example.Backend.schema.BookHeader;
import com.example.Backend.schema.BookInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.jsonSchema.factories.JsonSchemaFactory;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.networknt.schema.JsonSchema;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class Utils {
    private AuthorRepository authorRepository;

    private StudentRepository studentRepository;

    private PasswordEncoder passwordEncoder;

    private DisabilityRepository disabilityRepository;
    private AuthorSettingsRepository authorSettingsRepository;
    private StudentSettingsRepository studentSettingsRepository;
    public Utils() {
    }

    @Autowired
    public Utils(PasswordEncoder passwordEncoder,
                 StudentRepository studentRepository,
                 AuthorRepository authorRepository,
                 DisabilityRepository disabilityRepository,
                 StudentSettingsRepository studentSettingsRepository,
                 AuthorSettingsRepository authorSettingsRepository){
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.authorRepository = authorRepository;
        this.disabilityRepository = disabilityRepository;
        this.studentSettingsRepository = studentSettingsRepository;
        this.authorSettingsRepository = authorSettingsRepository;
    }
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Method getMethodBySignature(String prefix, Field field, Object callerObject, Class<?>... parametersTypes) throws NoSuchMethodException {
        String methodName = prefix +
                field.getName().substring(0,1).toUpperCase() +
                field.getName().substring(1);
        return  callerObject.getClass().getMethod(methodName,parametersTypes);
    }

    public void generateSomeUsers(){
//        authorSettingsRepository.deleteAll();
//        studentSettingsRepository.deleteAll();
//        authorRepository.deleteAll();
//        studentRepository.deleteAll();
        Author a = new Author("mahmoud",
                "mahmoudsaleem522@gmail.com",
                passwordEncoder.encode("123"));
        authorRepository.save(a);
        studentRepository.save(new Student(
                "mohamed",
                "mohamed@gmail.com",
                passwordEncoder.encode("456")
        ));
    }

    public void generateSomeDisabilities(){
        disabilityRepository.deleteAll();
        disabilityRepository
                .saveAll(
                        Arrays.asList(
                                new Disability("Blind"),
                                new Disability("Visually_Impaired"),
                                new Disability("Dyslexia")
                        )
                );
    }
    public  List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }
}
