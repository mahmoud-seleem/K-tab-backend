package com.example.Backend.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;

@Component
public class Utils {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Method getMethodBySignature(String prefix, Field field, Object callerObject, Class<?>... parametersTypes) throws NoSuchMethodException {
        String methodName = prefix +
                field.getName().substring(0,1).toUpperCase() +
                field.getName().substring(1);
        return  callerObject.getClass().getMethod(methodName,parametersTypes);
    }
}
