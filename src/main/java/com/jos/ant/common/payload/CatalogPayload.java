package com.jos.ant.common.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class CatalogPayload
{
    private Long catalogId;

    @NotNull @NotEmpty @NotBlank
    private String description;

    @NotNull @NotEmpty @NotBlank
    private String code;
}
