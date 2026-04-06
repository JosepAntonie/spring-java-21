package com.jos.ant.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

public interface WebRestControllerAdvice
{
    ResponseEntity<Map<String, Object>> handleValidationException( MethodArgumentNotValidException exception, HttpServletRequest request );
}
