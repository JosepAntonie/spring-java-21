package com.jos.ant.common.payload.request;

import com.jos.ant.common.validation.groups.OnSave;
import com.jos.ant.common.validation.groups.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

public record UserRequest(
        @Null( groups = OnSave.class ) @NotNull( groups = OnUpdate.class ) Long userId,
        @NotBlank( groups = { OnSave.class, OnUpdate.class } ) String username,
        @NotBlank( groups = { OnSave.class, OnUpdate.class } ) String passphrase,
        @NotBlank( groups = { OnSave.class, OnUpdate.class } ) String code,
        @NotNull( groups = { OnSave.class, OnUpdate.class } ) Boolean active,
        @NotNull( groups = { OnSave.class, OnUpdate.class } ) @Valid PersonRequest person,
        @NotEmpty( groups = { OnSave.class, OnUpdate.class } ) @Valid List<RoleRequest> roles
)
{
}
