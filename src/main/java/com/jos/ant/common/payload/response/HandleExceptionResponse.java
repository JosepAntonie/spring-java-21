package com.jos.ant.common.payload.response;

import lombok.Data;

@Data
public class HandleExceptionResponse
{
    private Integer status;
    private String error;
    private String code;
    private String reason;
    private String details;
}
