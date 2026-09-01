package com.jos.ant.common.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.LocalDate;

public record PersonResponse(
        Long personId,
        String name,
        String lastName,
        String lastNameMother,
        String mail,
        String telephone,
        @JsonFormat( pattern = "yyyy-MM-dd" ) LocalDate birthday,
        String createdBy,
        Instant createdDate,
        String lastModifiedBy,
        Instant lastModifiedDate
)
{
}
