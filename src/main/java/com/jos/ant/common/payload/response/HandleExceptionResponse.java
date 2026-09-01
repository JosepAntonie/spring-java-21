package com.jos.ant.common.payload.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public record HandleExceptionResponse (
        Integer status,
        String error,
        String code,
        String reason,
        String details
)
{
}