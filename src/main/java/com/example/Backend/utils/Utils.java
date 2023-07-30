package com.example.Backend.utils;

import com.example.Backend.Repository.*;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
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
                 AuthorSettingsRepository authorSettingsRepository) {
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
                field.getName().substring(0, 1).toUpperCase() +
                field.getName().substring(1);
        return callerObject.getClass().getMethod(methodName, parametersTypes);
    }

    public void generateSomeUsers() {
//        authorSettingsRepository.deleteAll();
//        studentSettingsRepository.deleteAll();
//        authorRepository.deleteAll();
//        studentRepository.deleteAll();
        Author a = new Author("mahmoud",
                "mahmoudsaleem522@gmail.com",
                passwordEncoder.encode("123"));
        AuthorSettings authorSettings = new AuthorSettings();
        authorSettingsRepository.save(authorSettings);
        a.setAuthorSettings(authorSettings);
        authorRepository.save(a);
        authorSettingsRepository.save(authorSettings);
        studentRepository.save(new Student(
                "mohamed",
                "mohamed@gmail.com",
                passwordEncoder.encode("456")
        ));
    }

    public void generateSomeDisabilities() {
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

    public List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    //    public static final String  image1 = "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";
//    public static final String image2 = "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";
//    public static final String image3 = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoKCgoKCgsMDAsPEA4QDxYUExMUFiIYGhgaGCIzICUgICUgMy03LCksNy1RQDg4QFFeT0pPXnFlZXGPiI+7u/sBCgoKCgoKCwwMCw8QDhAPFhQTExQWIhgaGBoYIjMgJSAgJSAzLTcsKSw3LVFAODhAUV5PSk9ecWVlcY+Ij7u7+//CABEIAGwAlgMBIgACEQEDEQH/xAAbAAADAQEBAQEAAAAAAAAAAAACAwQBAAUGB//aAAgBAQAAAAD8903sXPaoXr2lNOpzzxeyvJU9pehuzpsGRB07cfnIVXQuo47I5hluYyx/nkjSstlUqaIZqvQZ667eavrIfOnb5s0EzPfoa9hNekUQQpwh8Mrfr+gnxjioonlSBRfPNfZ7gRzz1t9Q9Au8r51TDPfpJti7X9DzIkqw+dj8r95aofMUOcvQ3ia9+z5k5s3UcCd1oGWKYCjqPgUM/HpKZrwoVxYAd0/EwK8Qeu4tAQAP/8QAGAEAAwEBAAAAAAAAAAAAAAAAAAECAwT/2gAKAgIQAxAAAADXlcuKGklKqaKKrIzsqVEyamiud65Hi8yApvZaLTPsOfTiqgMiS5ue2NyXjeTzuZ6ZtaKppypUOnQlLoa1cgSpSEN0n//EADEQAAIBAwMCBQMDBAMBAAAAAAECEQADIQQSMUFREyJhcYEUMkIFkaEQIzNyscHR4f/aAAgBAQABPwDM8VNbVcKGJicxSkEmKCCAqp1Jjkkmntoir9xMktOOe1L/AJBx88fNNoL9nTtqL7qMgJDBtxp7d21AddpYHBim8IKoVnL/AJBoAX0FeIzaZU8OLYcy4HJ5AmtlwpvCjYHAMmImgsBZBB9uRQNw2mRX22wQxTgGhAugXbTOsYExg9qe1pbVwiwhOZLnn2HYU1uxIKWSGiNxzFBzBPmVVxBGT60WNCDTUVhE9aRciZApFnEE1dSFJNOpBEwCMcdqJ8/IyZJIoOnlm2hVTJB/L3re7KFYwhYn0k0sNAwDx2rTOtq5vNm2xjBeTB5mKXxG8gfDPOTAmnsXbRh1WYONw/6pvxBZmgYnpXjoGIZJHGRNAiOaZ5OWmfSrttrhn6jb2WKU7uBFFC0bV3EnpV1IKLgQgIFLadbaByPFeWEmFAOFFWrRQKpuK7RllECewp1AgssgGYIwYq/cJcuYkndMd80bVtbTb94vNthCICqc7vnoKCW3uhd4tKxwWyB7xX02lW7i7cvWVADvaAEsR0DdKvnQDwU05uOqWTLMoUm4xnPOBS5MzNWmRATEmidyliqjtimEtEST2o37At+XSWUujCuOgPJz+VFBBY3SI4QDn5qYNOZ6UVV1gnBqzYs2rai07SwMgiKKEwSThWP84mrckqSxMKQM8UHW2gaGjdtHlOT2FXDhAyOGYgBSp3EnpFPpXICIF3TEMwEkn1ofot8xv1eiUzBHjAma+jUbx9xBhSMAweaWxcJOf39KXSvcaQsdMCKTRXFxBzX0G1QZintgNk4q7Yt23K27ouLyGAIx81D2zgKSehAI/mnaB8ZxT3CpAC5PFEkSDAIOQK3AFdwwMwDE1p9QB5JwcCDNG2QJGVGJOCfirfqsn0q1fu2Sp+0pIUHJX2B4pbl6/fiyjm4skkDcROJo6AC9eS48W7UhnUTngKJ71Z0umdyLjlEKwSq7iPgmr2j0puL9PItqoUbuTHLN6mk04ssfJbcHjeJApES00q3ycmrli9cteLAFsJvDSOKv3l2BQTI57VeuKSAtuF7TJJPc07MkqyFSOQcEUNPduJ4tvUaeett3Fth8PAIq++xmEAZ6HcP3oax7SXFXb5xBlQSPaeKLnoKdn9CSOAZ5q8GstDldyBQSvAMcfFaYG7p1vG5aO/AQGW9z2ra21EW2EIJlgDJJ6maFwhmZS3PlkSeeat667bsi3buGyBnyeXcx/JyOava1rl14uEoSPQNtEAmrN4EtLAAjJiYpLpUguCV5iYJrxXuLhJhDubkAd/SKVNRfJS0u9gN0LEwK23LS2HYlTcBcKQZ2gwCfejZd2McdKuO9h5D7XEQ0TzyZ7imuNu7DgseJrxbgCN7lPg814RcEls1a0Svn6myh4AdoJrbaVcnMxjNFjtGVjccdeOTSMo2EoHUficgnseKtatrV9ru1QHaSAIX2AqxcS8m9R5QFJP8AtwKuhSqnczNtz0A9B6AVedEbiccGmvNK25LbTAAM/tQ1cSIxQ1ixSa7arLuaG5UHB9xSarlhhhBUjoRRuX77tfu3muMzZdjM1bvSSD1HPary3gxKcERI7dafQl7dubxnMqRAX/2abQQCtsASAJIBbHrR0N1CQH3dmGKOjeDKgmrn09kxccA9qJmMCgMJIiRIPcTSFlIdbZhAGLQSvYGtLrn0y20X/GGZnzJYmk1tm4iNbbfMlgRgZ4rwrWquM1zVW7IkkypM/wCoWtVY01rYLGoe7IO5mTYB2AEzTIrB2WUUYUHzFiBkSKtkE+aYom2S2wPjK4H2jqYqy65MTTXtOSPCtbEAAALbj7k4mrt9DtFpYVRGeSe5pb/l2sMxgnkUGkglz3kGJotADjeEnmvqVXeOSRj0rVfqqKv9lpY1evNeuFyT6AkmPQTRkbCFHrmZz1oG9dKAHc7HaFngD/gUtxipR3YKVPBwY4FFkum648hwFTt/8AFI9ywQ1tiD17VptRZv2ACyC6VAbpmrhtJqBYXU2y6E+dBuHrn0pv0u4FlHUnOSelDTalXINtiBzTl5YLaZFnjJgVbKAZFNftQuwNMZnqaF3PWr2uuXLkK8KDz1NHX6liL107yx/LrFH9WuT5LKARGc1e1l+8IZoHYYpfCmbhaBnHX0pypyMZOO1FmMYA6YEUj3FMgdCP3EUGuI4IwRSMqOSAlyBC7gY94oFhRhcqSD/IqLeDDGOnetPr71kFYLjIA4itF+t+HaJdbSsAdj3BLLOCVq9q9HdbZcuhXGODR0ukALtdEDkyK1V7QhW+nZndR1wPjvV06tYS6WHlG3tHpFZKwNsL1gZnuaMHIUDpAoiBUEiuTUBeZqSeuAeK+5hwKWTUtMKJ6CltuziZ2Dk1p7ZttNzawHCnIooOB8npQKgwEg5liTJmm2JJkE0t0HLHcZlp61cuJkySSZM0txSSNtXLigBVLEDviJ5pEVgf7gGJg0UUY3ftVzTtaVGfAfK55HemE+1QAOaJQnkmp/aamg4zQuAAnYPtgdaLu3eluOM5o6hvmm1BbaCpiMkGpzgmmtuib2EAkxJzUrtggzWQaSTiCfQCaB2ShGAcitydFMd+tMyEZEUV7E1JTvQ2f0k1xGOlbjIJOBT3AS3hgqpjEzUnvQHGCDPNKGHAqFbhY7Uq72A7mBnFFiLbWljZu5gSR6ntWwHtSiDIkUIGTMe1F+QJrI4mjuoBjyKilzPt/SJNX0W3dKLwAP5FKAZrSWUvai1aaQrGDFGN7wIyYApiQzCaBO2gZE02IAxihxQAo9KmpNEmixrca//8QAHBEAAwEBAQADAAAAAAAAAAAAAAEREAIgMFFh/9oACAECAQE/APEJMhCCFimRfeQhNn4RwjIJDQ14WzW2V6umhdi6RYPor8ukZBIZWX4L4//EACURAQACAQQBAgcAAAAAAAAAAAEAAhEDEBIhMRNRICNBQmFxgf/aAAgBAwEBPwCLOUzMzlOUGDtbqLFnKN43IXGFoOzXJLddMf3LRbZzOPlZUIEqkLbaunzMnmI5wwoMdMIidQhax4ZQvZ6IaJjtdmalC53595bStUOD/GNrnmkPmPQsro1q5cM9LSftJUDx0TMZ37Swp9JwtOH5hQlidw+BQJnO2ZmDv//Z";
//    public static final String image4 = "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";
//    public static final String image5 = "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";
    public static final String[] IMAGES = {
            "https://learning.oreilly.com/library/cover/9781492056348/250w/",
            "https://learning.oreilly.com/library/cover/9781098108298/250w/",
            "https://learning.oreilly.com/library/cover/9781098134174/250w/",
            "https://learning.oreilly.com/library/cover/9781098156664/250w",
            "https://learning.oreilly.com/library/cover/9781492034018/250w/",
            "https://learning.oreilly.com/library/cover/9781098107628/250w/",
            "https://learning.oreilly.com/library/cover/9781617295355/250w/",
            "https://learning.oreilly.com/library/cover/9781789803259/250w/",
            "https://learning.oreilly.com/library/cover/9781439881415/250w/",
            "https://learning.oreilly.com/library/cover/9781439838372/250w/",
            "https://learning.oreilly.com/library/cover/9781138026568/250w/",
            "https://learning.oreilly.com/library/cover/9780123944368/250w/",
            "https://learning.oreilly.com/library/cover/9781118573648/250w/",
            "https://learning.oreilly.com/library/cover/9780470180938/250w/"
    };
    public static final String[] TITLES = {
            "Fluent Python",
            "Fundamentals of Data Engineering",
            "Generative Deep Learning, 2nd Edition",
            "Python Crash Course, 3rd Edition",
            "Building Microservices, 2nd Edition",
            "Essential Math for AI",
            "Math for Programmers",
            "Statistics for Data Science and Business Analysis",
            "Data-Intensive Science",
            "Knowledge Science",
            "Industrial, Mechanical and Manufacturing Science",
            "Security Science",
            "Applied Missing Data Analysis in the Health Sciences",
            "Elements of Computational Systems Biology"
    };

    public static final String[] authorNames = {
            "Mohamed Ibrahim",
            "Mariam Ashraf",
            "Nada Ahmed"
    };
    public static final String [] authorEmails = {
            "mohamed-ibrahim@k-tab.live",
            "mariam-ashraf@k-tab.live",
            "nada-ahmed@k-tab.live"
    };
    public static final String[] authorPasswords = {
            "12345678",
            "12345678",
            "12345678",
    };
    public static final String[] authorPhotos = {
    "https://randomuser.me/api/portraits/men/24.jpg",
    "https://randomuser.me/api/portraits/women/76.jpg",
    "https://randomuser.me/api/portraits/women/85.jpg"
    };

    public static final String studentName = "7amada";
    public static final String studentEmail = "7amada@k-tab.live";
    public static final String studentPassword = "12345678";
    public static final String studentImage = "https://randomuser.me/api/portraits/men/22.jpg";
    public static final String[] TagsNames = {"MATH","SCIENCE","TECH","BIO","ENG","LIT","CODE","DEV"};
}
