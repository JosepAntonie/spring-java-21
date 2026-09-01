package com.jos.ant.common.payload.request;

public record FilterRequest<U>(
        Integer pageNumber,
        Integer pageSize,
        String search,
        U request
)
{
}
