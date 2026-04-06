package com.jos.ant.web.advice.impl;

import com.jos.ant.web.advice.WebRestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestControllerAdvice
public class WebRestControllerAdviceImpl  implements WebRestControllerAdvice
{

    @ExceptionHandler( MethodArgumentNotValidException.class )
    public ResponseEntity<Map<String, Object>> handleValidationException( MethodArgumentNotValidException exception, HttpServletRequest request )
    {
        log.info( "WebRestControllerAdviceImpl -> handleValidationException \n{}", exception.getMessage() );

        Map<String, String> json = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach( fieldError -> json.put( fieldError.getField(), fieldError.getDefaultMessage() ) );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( getJson( json, request.getServletPath() ) );
    }

    private Map<String, Object> getJson( Object message, String path )
    {
        Map<String, Object> json = new HashMap<>();
        json.put( "status", HttpStatus.BAD_REQUEST.value() );
        json.put( "error", HttpStatus.BAD_REQUEST.getReasonPhrase() );
        json.put( "message", message );
        json.put( "path", path );
        return json;
    }
}
