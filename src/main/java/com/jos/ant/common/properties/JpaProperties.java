package com.jos.ant.common.properties;

public record JpaProperties(
        HibernateProperties hibernate,
        String databasePlatform,
        Boolean generateDdl,
        Boolean showSql
)
{
    public record HibernateProperties(
            String ddlAuto,
            NamingProperties naming
    )
    {
    }

    public record NamingProperties(
            String physicalStrategy
    )
    {
    }
}
