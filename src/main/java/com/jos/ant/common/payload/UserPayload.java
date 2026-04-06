package com.jos.ant.common.payload;

import com.jos.ant.common.validation.groups.OnSave;
import com.jos.ant.common.validation.groups.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode( callSuper = true )
public class UserPayload extends AuditingPayload<String>
{
    @Null( groups = OnSave.class )  @NotNull( groups = OnUpdate.class )
    private Long userId;

    @NotNull @NotEmpty @NotBlank
    private String username;

    @NotNull @NotEmpty @NotBlank
    private String passphrase;

    @NotNull @NotEmpty @NotBlank
    private String code;

    @NotNull
    private Boolean active;

    @NotNull @Valid
    private PersonPayload person;

    @NotNull @NotEmpty @Valid
    private List<RolePayload> roles;
}
