package com.jos.ant.repository.mssql;

import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import com.jos.ant.repository.mssql.entity.UserEntity;
import com.jos.ant.repository.mssql.impl.UserRepositoryImpl;
import com.jos.ant.repository.mssql.jpa.UserJpa;
import com.jos.ant.repository.mssql.mapper.UserMapper;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( MockitoExtension.class )
class UserRepositoryTest
{
    private UserRepository repository;
    private UserMapper mapper;
    private UserJpa jpa;

    @BeforeEach
    void setUp()
    {
        jpa = mock( UserJpa.class );
        mapper = mock( UserMapper.class );
        repository = new UserRepositoryImpl( jpa, mapper );
    }

    @Test
    void findAll()
    {
        when( jpa.findAll() ).thenReturn( Collections.emptyList() );
        when( mapper.toUserResponseList( anyList() ) ).thenReturn( Collections.emptyList() );
        assertNotNull( repository.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        when( jpa.findAll( any( BooleanBuilder.class ), any( PageRequest.class ) ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        when( mapper.toUserResponseList( anyList() ) ).thenReturn( Collections.emptyList() );
        assertNotNull( repository.findAllByFilter( getFilterPayload() ) );
    }
    private FilterRequest<UserRequest> getFilterPayload()
    {
        return new FilterRequest<>( 0, 10, null, mock( UserRequest.class ) );
    }

    @Test
    void findById()
    {
        when( jpa.findById( anyLong() ) ).thenReturn( Optional.of( new UserEntity() ) );
        when( mapper.toUserResponse( any() ) ).thenReturn( mock( UserResponse.class ) );
        assertNotNull( repository.findById( 1L ) );
    }

    @Test
    void save()
    {
        when( mapper.toUserEntity( any( UserRequest.class ) ) ).thenReturn( new UserEntity() );
        when( jpa.save( any( UserEntity.class ) ) ).thenReturn(  new UserEntity() );
        when( mapper.toUserResponse( any( UserEntity.class ) ) ).thenReturn( mock( UserResponse.class ) );
        assertNotNull( repository.save( mock( UserRequest.class ) ) );
    }

    @Test
    void deleteById()
    {
        repository.deleteById( 1L );
        verify( jpa, times( 1 ) ).deleteById( 1L );
    }
}
