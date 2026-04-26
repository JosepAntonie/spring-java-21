package com.jos.ant.repository.mssql;

import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.PersonPayload;
import com.jos.ant.common.payload.RolePayload;
import com.jos.ant.common.payload.UserPayload;
import com.jos.ant.repository.mssql.entity.UserEntity;
import com.jos.ant.repository.mssql.impl.UserRepositoryImpl;
import com.jos.ant.repository.mssql.jpa.UserJpa;
import com.jos.ant.repository.mssql.mapper.UserMapper;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest( classes = UserRepository.class )
class UserRepositoryTest
{
    private UserRepository repository;
    private UserMapper mapper;
    private UserJpa jpa;

    @BeforeEach
    void setUp()
    {
        jpa = Mockito.mock( UserJpa.class );
        mapper = Mockito.mock( UserMapper.class );
        repository = new UserRepositoryImpl( jpa, mapper );
    }

    @Test
    void findAll()
    {
        Mockito.when( jpa.findAll() ).thenReturn( Collections.emptyList() );
        Mockito.when( mapper.toUserPayloadList( Mockito.anyList() ) ).thenReturn( Collections.emptyList() );
        Assertions.assertNotNull( repository.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        Mockito.when( jpa.findAll( Mockito.any( BooleanBuilder.class ), Mockito.any( PageRequest.class ) ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        Mockito.when( mapper.toUserPayloadList( Mockito.anyList() ) ).thenReturn( Collections.emptyList() );
        Assertions.assertNotNull( repository.findAllByFilter( getFilterPayload() ) );
    }
    private FilterPayload<UserPayload> getFilterPayload()
    {
        FilterPayload<UserPayload> filter = new FilterPayload<>();
        filter.setPageNumber( 0 );
        filter.setPageSize( 10 );
        UserPayload userPayload = new UserPayload();
        userPayload.setRoles( Collections.singletonList( new RolePayload() ) );
        userPayload.setPerson( new PersonPayload() );
        filter.setPayload( new UserPayload() );
        return filter;
    }

    @Test
    void findById()
    {
        Mockito.when( jpa.findById( Mockito.anyLong() ) ).thenReturn( Optional.of( new  UserEntity() ) );
        Mockito.when( mapper.toUserPayload( Mockito.any() ) ).thenReturn( new UserPayload() );
        Assertions.assertNotNull( repository.findById( 1L ) );
    }

    @Test
    void save()
    {
        Mockito.when( mapper.toUserEntity( Mockito.any( UserPayload.class ) ) ).thenReturn( new UserEntity() );
        Mockito.when( jpa.save( Mockito.any( UserEntity.class ) ) ).thenReturn(  new UserEntity() );
        Mockito.when( mapper.toUserPayload( Mockito.any( UserEntity.class ) ) ).thenReturn( new UserPayload() );
        Assertions.assertNotNull( repository.save( new UserPayload() ) );
    }

    @Test
    void deleteById()
    {
        repository.deleteById( 1L );
        Mockito.verify( jpa, Mockito.times( 1 ) ).deleteById( 1L );
    }
}
