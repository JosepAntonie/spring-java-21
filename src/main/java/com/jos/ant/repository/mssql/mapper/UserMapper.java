package com.jos.ant.repository.mssql.mapper;

import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import com.jos.ant.repository.mssql.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper( componentModel = "spring", uses = { PersonMapper.class, RoleMapper.class } )
public interface UserMapper
{
    UserEntity toUserEntity( UserRequest userRequest );
    UserResponse toUserResponse( UserEntity userEntity );
    List<UserResponse> toUserResponseList( List<UserEntity> userEntityList );
}