package com.jos.ant.common.payload.request;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        Long catalogId,
        @NotBlank String description,
        @NotBlank String code
)
{
}
