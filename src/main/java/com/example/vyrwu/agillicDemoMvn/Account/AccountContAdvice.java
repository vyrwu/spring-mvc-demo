package com.example.vyrwu.agillicDemoMvn.Account;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


@Aspect
@Component
public class AccountContAdvice {
    private static final Map<Class<? extends Exception>, String> typeMap
            = new HashMap<Class<? extends Exception>, String>() {
        {
            put(NullPointerException.class, "haha");
            put(IllegalArgumentException.class, "haha");
            put(Exception.class, "haha");
            put(IOException.class, "haha");
            put(RuntimeException.class, "haha");
        }
    };

    private static String getResponseFor(Exception exception) {
        Class exceptionClass = exception.getClass();
        if (!typeMap.containsKey(exceptionClass)) {
            return "null";
        }
        return typeMap.get(exceptionClass);
    }

    @AfterThrowing(pointcut = "@annotation(ExceptionsHandled)", throwing = "exception")
    public ResponseEntity handle(Exception exception) {
        return ResponseEntity.ok().body(getResponseFor(exception));
    }
}
