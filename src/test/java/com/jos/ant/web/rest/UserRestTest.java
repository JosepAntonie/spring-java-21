package com.jos.ant.web.rest;

import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
import com.jos.ant.service.UserService;
import com.jos.ant.web.rest.impl.UserRestImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;

@ExtendWith( MockitoExtension.class )
class UserRestTest
{
    private UserRest rest;
    private UserService service;

    @BeforeEach
    void setUp()
    {
        service = Mockito.mock( UserService.class );
        rest = new UserRestImpl( service );
    }

    @Test
    void findAll()
    {
        Mockito.when( service.findAll() ).thenReturn( Collections.emptyList() );
        Assertions.assertNotNull( rest.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        Mockito.when( service.findAllByFilter( Mockito.any() ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        Assertions.assertNotNull( rest.findAllByFilter( new FilterPayload<>() ) );
    }

    @Test
    void findById()
    {
        Mockito.when( service.findById( Mockito.anyLong() ) ).thenReturn( Optional.of( new UserPayload() ) );
        Assertions.assertNotNull( rest.findById( 1L ) );
    }

    @Test
    void save()
    {
        Mockito.when( service.save( Mockito.any() ) ).thenReturn( new UserPayload() );
        Assertions.assertNotNull( rest.save( new UserPayload() ) );
    }

    @Test
    void update()
    {
        Mockito.when( service.update( Mockito.any() ) ).thenReturn( new UserPayload() );
        Assertions.assertNotNull( rest.update( new UserPayload() ) );
    }

    @Test
    void deleteById()
    {
        Mockito.when( service.deleteById( Mockito.anyLong() ) ).thenReturn( true );
        Assertions.assertNotNull( rest.deleteById( 1L ) );
    }
}
