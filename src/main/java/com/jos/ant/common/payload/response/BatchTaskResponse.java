package com.jos.ant.common.payload.response;

public record BatchTaskResponse(
        Long taskId,
        String taskName,
        String cron,
        Boolean active
)
{
}
