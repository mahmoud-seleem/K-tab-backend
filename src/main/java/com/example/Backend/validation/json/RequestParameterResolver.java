package com.example.Backend.validation.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParameterResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper objectMapper;
    private final ResourcePatternResolver resourcePatternResolver;
    private final Map<String, JsonSchema> schemaCache;

    public RequestParameterResolver(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver) {
        this.objectMapper = objectMapper;
        this.resourcePatternResolver = resourcePatternResolver;
        this.schemaCache = new ConcurrentHashMap<>();
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ValidParam.class) != null;
    }
    @Override
    public Object resolveArgument(MethodParameter parameter
            , ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest
            , WebDataBinderFactory binderFactory) throws Exception {
        Object res = null;
        Class<?> type = parameter.getParameterType();
        String paramValue = webRequest.getParameter(parameter.getParameterName());
        System.out.println(type.getName());
        System.out.println(paramValue);
        if (paramValue == null) {
            return null;
        } else {
            switch (type.getName()) {
                case "java.lang.String":
                    res = paramValue;
                    break;
                case "int":
                    try {
                        res = Integer.parseInt(paramValue);
                    } catch (Exception e) {
                        throw new InvalidParameterException(
                                parameter.getParameterName(),
                                paramValue,
                                "parameter " +
                                        parameter.getParameterName() +
                                        " needs an int value");
                    }
                    break;
                case "double":
                    try {
                        res = Double.parseDouble(paramValue);
                    } catch (Exception e) {
                        throw new InvalidParameterException(
                                parameter.getParameterName(),
                                paramValue,
                                "parameter " +
                                        parameter.getParameterName() +
                                        " needs double value");
                    }
                    break;
                case "boolean":
                    try {
                        res = Boolean.getBoolean(paramValue);
                    } catch (Exception e) {
                        throw new InvalidParameterException(
                                parameter.getParameterName(),
                                paramValue,
                                "parameter " +
                                        parameter.getParameterName() +
                                        " needs boolean value");
                    }
                    break;
                case "java.util.UUID":
                    try {
                        res = UUID.fromString(paramValue);
                    } catch (Exception e) {
                        throw new InvalidParameterException(
                                parameter.getParameterName(),
                                paramValue,
                                "parameter " +
                                        parameter.getParameterName() +
                                        " needs UUID value");
                    }
                    break;
                default:
                    res = paramValue;
            }
        }
        return res;
    }
}
