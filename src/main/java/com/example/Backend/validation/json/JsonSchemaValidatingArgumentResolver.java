package com.example.Backend.validation.json;

import com.example.Backend.schema.BookInfo;
import com.example.Backend.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.github.reinert.jjschema.*;
import com.github.victools.jsonschema.generator.*;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JsonSchemaValidatingArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;
    private final ResourcePatternResolver resourcePatternResolver;
    private final Map<String, JsonSchema> schemaCache;

    public JsonSchemaValidatingArgumentResolver(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver) {
        this.objectMapper = objectMapper;
        this.resourcePatternResolver = resourcePatternResolver;
        this.schemaCache = new ConcurrentHashMap<>();
    }
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ValidJson.class) != null;
    }

    @Override
    public Object resolveArgument (
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception{
        com.networknt.schema.JsonSchema schema =
                getJsonSchema(
                        parameter.getParameterAnnotation(
                                ValidJson.class).value());
        // parse json payload
        JsonNode json = objectMapper.readTree(getJsonPayload(webRequest));
        // Do actual validation
        Set<ValidationMessage> validationResult = schema.validate(json);
        if (validationResult.isEmpty()) {
            // No validation errors, convert JsonNode to method parameter type and return it
            return objectMapper.treeToValue(json, parameter.getParameterType());
        }
        // throw exception if validation failed
        throw new JsonValidationFailedException(validationResult);
    }
    private String getJsonPayload(NativeWebRequest nativeWebRequest) throws IOException {
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        return StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
    }
    private com.networknt.schema.JsonSchema getJsonSchema(String schemaName) throws ClassNotFoundException {
        return generateSchema(schemaName);
    }
    private com.networknt.schema.JsonSchema generateSchema(
            String className) throws ClassNotFoundException {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_6, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        com.networknt.schema.JsonSchemaFactory schemaFactory = com.networknt.schema.JsonSchemaFactory.getInstance(
                SpecVersion.VersionFlag.V6);
        Class c = Class.forName("com.example.Backend.schema."+className);
//        JsonNode schemaNode = v4generator.generateSchema(c);
        JsonNode jsonSchema = generator.generateSchema(c);
        return schemaFactory.getSchema(jsonSchema);
    }
    }