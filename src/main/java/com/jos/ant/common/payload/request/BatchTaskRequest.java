package com.jos.ant.common.payload.request;

import com.jos.ant.common.validation.groups.OnSave;
import com.jos.ant.common.validation.groups.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record BatchTaskRequest(
        @Null( groups = OnSave.class ) @NotNull( groups = OnUpdate.class ) Long taskId,
        @NotBlank( groups = { OnSave.class, OnUpdate.class } ) String taskName,
        @NotBlank( groups = { OnSave.class, OnUpdate.class } ) String cron,
        @NotNull( groups = { OnSave.class, OnUpdate.class } ) Boolean active
)
{
}
