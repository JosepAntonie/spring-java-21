package com.jos.ant.web.rest;

import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import com.jos.ant.service.UserService;
import com.jos.ant.web.rest.impl.UserRestImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( MockitoExtension.class )
class UserRestTest
{
    private UserRest rest;
    private UserService service;

    @BeforeEach
    void setUp()
    {
        service = mock( UserService.class );
        rest = new UserRestImpl( service );
    }

    @Test
    void findAll()
    {
        when( service.findAll() ).thenReturn( Collections.emptyList() );
        assertNotNull( rest.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        when( service.findAllByFilter( any() ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        assertNotNull( rest.findAllByFilter( new FilterRequest<>( 0, 10, null, mock( UserRequest.class ) ) ) );
    }

    @Test
    void findById()
    {
        when( service.findById( anyLong() ) ).thenReturn( Optional.of( mock( UserResponse.class ) ) );
        assertNotNull( rest.findById( 1L ) );
    }

    @Test
    void save()
    {
        when( service.save( any() ) ).thenReturn( mock( UserResponse.class ) );
        assertNotNull( rest.save( mock( UserRequest.class ) ) );
    }

    @Test
    void update()
    {
        when( service.update( any() ) ).thenReturn( mock( UserResponse.class ) );
        assertNotNull( rest.update( mock( UserRequest.class ) ) );
    }

    @Test
    void deleteById()
    {
        when( service.deleteById( anyLong() ) ).thenReturn( true );
        assertNotNull( rest.deleteById( 1L ) );
    }
}
