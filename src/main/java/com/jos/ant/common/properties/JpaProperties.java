package com.jos.ant.common.properties;

public record JpaProperties(
        String ddlAuto,
        String databasePlatform,
        String physicalStrategy,
        Boolean generateDdl,
        Boolean showSql
)
{
}
