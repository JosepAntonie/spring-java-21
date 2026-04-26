package com.jos.ant.repository.mssql.impl;

import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
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

    public List<UserPayload> findAll()
    {
        log.info( "Repository -> FindAll" );
        return userMapper.toUserPayloadList( userJpa.findAll() );
    }

    public Page<UserPayload> findAllByFilter( FilterPayload<UserPayload> filter )
    {
        log.info( "Repository -> FindAllByFilter" );
        PageRequest pageRequest = PageRequest.of( filter.getPageNumber(), filter.getPageSize(), Sort.by( Sort.Order.asc( "userId" ) ) );
        Page<UserEntity> usuarioPage = userJpa.findAll( UserPredicate.findAllByFilter( filter.getPayload(), filter.getSearch() ), pageRequest );
        return new PageImpl<>( userMapper.toUserPayloadList( usuarioPage.getContent() ), pageRequest, usuarioPage.getTotalElements() );
    }

    public Optional<UserPayload> findById( Long userId )
    {
        log.info( "UserRepository -> FindById" );
        return Optional.ofNullable( userMapper.toUserPayload( userJpa.findById( userId ).orElse( null ) ) );
    }

    public UserPayload save( UserPayload userPayload )
    {
        log.info( "UserRepository -> Save" );
        return userMapper.toUserPayload( userJpa.save( userMapper.toUserEntity( userPayload ) ) );
    }

    public void deleteById( Long userId )
    {
        log.info( "UserRepository -> Delete" );
        userJpa.deleteById( userId );
    }
}
