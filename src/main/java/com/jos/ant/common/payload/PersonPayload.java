package com.jos.ant.common.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode( callSuper = true )
public class PersonPayload extends AuditingPayload<String>
{
    private Long personId;

    @NotNull @NotEmpty @NotBlank
    private String name;

    @NotNull @NotEmpty @NotBlank
    private String lastName;

    @NotNull @NotEmpty @NotBlank
    private String lastNameMother;

    @NotNull @NotEmpty @NotBlank @Email
    private String mail;

    @NotNull @NotEmpty @NotBlank @Pattern( regexp = "^\\d{10}$", message = "El telefono debe tener 10 dígitos" )
    private String telephone;

    @NotNull @Past @JsonFormat( pattern = "yyyy-MM-dd" )
    private LocalDate birthday;
}
