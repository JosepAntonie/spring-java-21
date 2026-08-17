package com.jos.ant.repository.mssql.impl;

import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import com.jos.ant.repository.mssql.UserRepository;
import com.jos.ant.repository.mssql.entity.UserEntity;
import com.jos.ant.repository.mssql.jpa.UserJpa;
import com.jos.ant.repository.mssql.mapper.UserMapper;
import com.jos.ant.repository.mssql.predicate.UserPredicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository
{
    private final UserJpa userJpa;
    private final UserMapper userMapper;

    public List<UserResponse> findAll()
    {
        log.info( "Repository -> FindAll" );
        return userMapper.toUserResponseList( userJpa.findAll() );
    }

    public Page<UserResponse> findAllByFilter( FilterRequest<UserRequest> filterRequest )
    {
        log.info( "Repository -> FindAllByFilter" );
        PageRequest pageRequest = PageRequest.of( filterRequest.pageNumber(), filterRequest.pageSize(), Sort.by( Sort.Order.asc( "userId" ) ) );
        Page<UserEntity> usuarioPage = userJpa.findAll( UserPredicate.findAllByFilter( filterRequest.request(), filterRequest.search() ), pageRequest );
        return new PageImpl<>( userMapper.toUserResponseList( usuarioPage.getContent() ), pageRequest, usuarioPage.getTotalElements() );
    }

    public Optional<UserResponse> findById( Long userId )
    {
        log.info( "UserRepository -> FindById" );
        return Optional.ofNullable( userMapper.toUserResponse( userJpa.findById( userId ).orElse( null ) ) );
    }

    public UserResponse save( UserRequest userRequest )
    {
        log.info( "UserRepository -> Save" );
        return userMapper.toUserResponse( userJpa.save( userMapper.toUserEntity( userRequest ) ) );
    }

    public void deleteById( Long userId )
    {
        log.info( "UserRepository -> Delete" );
        userJpa.deleteById( userId );
    }
}
