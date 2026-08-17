package com.jos.ant.common.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PersonRequest(
        Long personId,
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank String lastNameMother,
        @NotBlank @Email String mail,
        @NotBlank @Pattern( regexp = "^\\d{10}$", message = "El teléfono debe tener 10 dígitos" ) String telephone,
        @NotNull @Past @JsonFormat( pattern = "yyyy-MM-dd" ) LocalDate birthday
)
{
}
