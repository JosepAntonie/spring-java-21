package com.jos.ant.common.exception;

import com.jos.ant.common.payload.response.HandleExceptionResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseException extends ApplicationException
{
    private final transient HandleExceptionResponse response;

    public ResponseException()
    {
        super();
        this.response = null;
    }
    public ResponseException( HandleExceptionResponse response )
    {
        super();
        this.response = response;
    }
    public ResponseException( String message, HandleExceptionResponse response )
    {
        super( message );
        this.response = response;
    }
    public ResponseException( String message, HandleExceptionResponse response, Throwable cause )
    {
        super( message, cause );
        this.response = response;
    }
    public ResponseException( HttpStatus httpStatus, String code, String message, String description, Throwable cause )
    {
        super( message, cause );
        this.response = createResponse( httpStatus, code, message, description );
    }
    public ResponseException( HttpStatus httpStatus, String code, String message, String description )
    {
        super( message );
        this.response = createResponse( httpStatus, code, message, description );
    }

    private HandleExceptionResponse createResponse( HttpStatus status, String code, String reason, String details )
    {
        HandleExceptionResponse newResponse = new HandleExceptionResponse();
        newResponse.setStatus( status.value() );
        newResponse.setError( status.name() );
        newResponse.setCode( code );
        newResponse.setReason( reason );
        newResponse.setDetails( details );
        return newResponse;
    }
}
