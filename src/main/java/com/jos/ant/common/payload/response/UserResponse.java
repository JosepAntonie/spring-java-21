package com.jos.ant.common.payload.response;

import java.time.Instant;
import java.util.List;

public record UserResponse(
        Long userId,
        String username,
        String passphrase,
        String code,
        Boolean active,
        PersonResponse person,
        List<RoleResponse> roles,
        String createdBy,
        Instant createdDate,
        String lastModifiedBy,
        Instant lastModifiedDate
)
{
}
