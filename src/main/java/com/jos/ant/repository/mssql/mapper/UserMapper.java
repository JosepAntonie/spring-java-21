package com.jos.ant.repository.mssql.mapper;

import com.jos.ant.common.payload.UserPayload;
import com.jos.ant.repository.mssql.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper( componentModel = "spring", uses = { PersonMapper.class, RoleMapper.class } )
public interface UserMapper
{
    @Mapping( source = "userId", target = "userId" )
    @Mapping( source = "username", target = "username" )
    @Mapping( source = "passphrase", target = "passphrase" )
    @Mapping( source = "code", target = "code" )
    @Mapping( source = "roles", target = "roles" )
    @Mapping( source = "active", target = "active" )
    @Mapping( source = "person", target = "person" )
    @Mapping( source = "createdBy", target = "createdBy" )
    @Mapping( source = "createdDate", target = "createdDate" )
    @Mapping( source = "lastModifiedBy", target = "lastModifiedBy" )
    @Mapping( source = "lastModifiedDate", target = "lastModifiedDate" )
    UserPayload toUserPayload( UserEntity user );
    List<UserPayload> toUserPayloadList( List<UserEntity> userList );

    @InheritInverseConfiguration
    UserEntity toUserEntity( UserPayload user );
}