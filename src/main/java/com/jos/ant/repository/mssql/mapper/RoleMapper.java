package com.jos.ant.repository.mssql.mapper;

import com.jos.ant.common.payload.request.RoleRequest;
import com.jos.ant.common.payload.response.RoleResponse;
import com.jos.ant.repository.mssql.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper( componentModel = "spring" )
public interface RoleMapper
{
    RoleEntity toRoleEntity( RoleRequest roleRequest );
    RoleResponse toRoleResponse( RoleEntity roleEntity );
    List<RoleResponse> toRoleResponseList( List<RoleEntity> roleEntityList );
}