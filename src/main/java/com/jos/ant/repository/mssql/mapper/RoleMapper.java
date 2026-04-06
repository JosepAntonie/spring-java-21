package com.jos.ant.repository.mssql.mapper;

import com.jos.ant.common.payload.RolePayload;
import com.jos.ant.repository.mssql.entity.RoleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper( componentModel = "spring" )
public interface RoleMapper
{
    @Mapping( source = "catalogId", target = "catalogId" )
    @Mapping( source = "description", target = "description" )
    @Mapping( source = "code", target = "code" )
    RolePayload toRolePayload( RoleEntity role );
    List<RolePayload> toRolePayloadList( List<RoleEntity> roles );

    @InheritInverseConfiguration
    RoleEntity toRoleEntity(RolePayload role );
}