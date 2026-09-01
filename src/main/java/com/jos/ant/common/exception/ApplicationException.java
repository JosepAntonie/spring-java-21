package com.jos.ant.common.exception;

public class ApplicationException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "Default Exception, excepcion lanzada sin descripcion.";

    public ApplicationException()
    {
        super( DEFAULT_MESSAGE );
    }
    public ApplicationException( String message )
    {
        super( message );
    }
    public ApplicationException( Throwable cause )
    {
        super( cause );
    }
    public ApplicationException( String message, Throwable cause )
    {
        super( message, cause );
    }
}