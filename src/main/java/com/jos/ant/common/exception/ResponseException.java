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
    public ResponseException( Throwable cause, HandleExceptionResponse response )
    {
        super( cause );
        this.response = response;
    }
    public ResponseException( String message, Throwable cause, HandleExceptionResponse response )
    {
        super( message, cause );
        this.response = response;
    }
    public ResponseException( HttpStatus httpStatus, String code, String message, String description )
    {
        super( message );
        this.response = createResponse( httpStatus, code, message, description );
    }
    public ResponseException( HttpStatus httpStatus, String code, String message, String description, Throwable cause )
    {
        super( message, cause );
        this.response = createResponse( httpStatus, code, message, description );
    }

    private static HandleExceptionResponse createResponse( HttpStatus status, String code, String reason, String details )
    {
        return new HandleExceptionResponse( status.value(), status.name(), code, reason, details );
    }
}
